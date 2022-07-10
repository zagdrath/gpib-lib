/*
 * @(#)PrologixEthernet.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 06, 2022
 * Updated: July 07, 2022
 */

package gpiblib;

import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.script.ScriptException;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import java.net.UnknownServiceException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.LinkedBlockingQueue;

import gpiblib.util.ReadlineTerminationMode;

public class PrologixEthernet {
    private URL prologixURL;
    private URLConnection prologixURLConnection;

    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    /**
     * Constructs and configures the Prologix Ethernet.
     * 
     * @param prologixURL
     */
    public PrologixEthernet(URL prologixURL)
            throws IOException, IllegalArgumentException, ScriptException {
        if (prologixURL == null) {
            throw new IllegalArgumentException("ERROR: Invalid URL");
        } else {
            this.prologixURL = prologixURL;
        }

        openConnection(prologixURL);

        defaultConfig();
    }

    public PrologixEthernet() {
        
    }

    private void defaultConfig() throws IOException {
        prologixWriteCommand(modeCommand + " 1"); // Set to controller mode
        prologixWriteCommand(ifcCommand); // Set to controller in charge
        prologixWriteCommand(eoiCommand + " 1"); // Enable EOI assertion with last character
        prologixWriteCommand(eosCommand + " 3"); // Do not append anything
        prologixWriteCommand(eotEnableCommand + " 0"); // Do not append any character
        prologixWriteCommand(autoCommand + " 0"); // Do not auto address instruments
    }

    // Addressing

    /**
     * Get's the URL.
     * 
     * @return Prologix URL
     */
    public URL getProglogixURL() {
        return prologixURL;
    }

    /**
     * Set's the URL.
     * 
     * @param prologixURL
     */
    public void setPrologixURL(URL prologixURL) {
        this.prologixURL = prologixURL;
    }

    // Network Connections

    /**
     * Opens the network connection to the Prologix Ethernet.
     * 
     * @param prologixURL
     */
    public void openConnection(URL prologixURL) throws ScriptException {
        prologixURLConnection = null;

        try {
            prologixURLConnection = prologixURL.openConnection();
            prologixURLConnection.setDoOutput(true);
            prologixURLConnection.connect();

            inputStream = new BufferedReader(
                    new InputStreamReader(prologixURLConnection.getInputStream()));
            outputStream = new BufferedWriter(
                    new OutputStreamWriter(prologixURLConnection.getOutputStream()));
        } catch (NullPointerException e) {
            throw new ScriptException("ERROR: Could not Open Network Connection");
        } catch (UnknownServiceException e) {
            throw new ScriptException("ERROR: Could not Open Network Connection");
        } catch (IOException e) {
            throw new ScriptException("ERROR: Could not Open Network Connection");
        }
    }

    /**
     * Closes the network connection to the Prologix Ethernet.
     */
    public void closeConnection() throws IOException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("ERROR: Could not Close Network Connection");
        }
    }

    // Prologix Ethernet Commands

    private final static String addressCommand = "++addr";
    private final static String autoCommand = "++auto";
    private final static String clearCommand = "++clr";
    private final static String eoiCommand = "++eoi";
    private final static String eosCommand = "++eos";
    private final static String eotEnableCommand = "++eot_enable";
    private final static String eotCharCommand = "++eot_char";
    private final static String ifcCommand = "++ifc";
    private final static String lloCommand = "++llo";
    private final static String locCommand = "++loc";
    private final static String lonCommand = "++lon";
    private final static String modeCommand = "++mode";
    private final static String readCommand = "++read";
    private final static String readTmoMsCommand = "++read_tmo_ms";
    private final static String resetCommand = "++rst";
    private final static String saveConfigCommand = "++savecfg";
    private final static String spollCommand = "++spoll";
    private final static String srqCommand = "++srq";
    private final static String statusCommand = "++status";
    private final static String triggerCommand = "++trg";
    private final static String versionCommand = "++ver";
    private final static String helpCommand = "++help";

    // Prologix Read Raw

    private LinkedBlockingQueue<Byte> readBytes = new LinkedBlockingQueue<>();

    private byte prologixReadByte(long timeoutMs) throws TimeoutException, InterruptedException {
        Byte byteRead = (byte) this.readBytes.poll(timeoutMs, TimeUnit.MILLISECONDS);

        if (byteRead == null) {
            throw new TimeoutException();
        }

        return byteRead;
    }

    private final static ReadlineTerminationMode readlineTerminationMode =
            ReadlineTerminationMode.OPTCR_LF;

    public byte[] prologixReadLine(ReadlineTerminationMode readlineTerminationMode, long timeoutMs)
            throws IOException, InterruptedException, TimeoutException {
        if (timeoutMs <= 0) {
            throw new TimeoutException();
        }

        long deadlineMillis = System.currentTimeMillis() + timeoutMs;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (;;) {
            long currentMillis = System.currentTimeMillis();

            if (byteArrayOutputStream.size() > 0) {
                throw new TimeoutException();
            }

            byte byteRead = prologixReadByte(deadlineMillis - currentMillis);

            currentMillis = System.currentTimeMillis();

            switch (readlineTerminationMode) {
                case CR: {
                    if (byteRead == 0x0d) {
                        byte[] bytesRead = byteArrayOutputStream.toByteArray();

                        return bytesRead;
                    } else {
                        byteArrayOutputStream.write(byteRead);
                    }

                    break;
                }

                case LF: {
                    if (byteRead == 0x0a) {
                        byte[] bytesRead = byteArrayOutputStream.toByteArray();

                        return bytesRead;
                    } else {
                        byteArrayOutputStream.write(byteRead);
                    }

                    break;
                }

                case CR_LF: {
                    if (byteRead == 0x0d) {
                        byte nextByteRead = prologixReadByte(deadlineMillis - currentMillis);

                        if (nextByteRead == 0x0a) {
                            byte[] bytesRead = byteArrayOutputStream.toByteArray();

                            byteArrayOutputStream.write(byteRead);
                            byteArrayOutputStream.write(nextByteRead);

                            return bytesRead;
                        } else {
                            throw new IOException();
                        }
                    } else {
                        byteArrayOutputStream.write(byteRead);
                    }

                    break;
                }

                case OPTCR_LF: {
                    switch (byteRead) {
                        case 0x0d: {
                            byte nextByteRead = prologixReadByte(deadlineMillis - currentMillis);

                            if (nextByteRead == 0x0a) {
                                byte[] bytesRead = byteArrayOutputStream.toByteArray();

                                byteArrayOutputStream.write(byteRead);
                                byteArrayOutputStream.write(nextByteRead);

                                return bytesRead;
                            } else {
                                byteArrayOutputStream.write(byteRead);
                                byteArrayOutputStream.write(nextByteRead);

                                throw new IOException();
                            }
                        }

                        case 0x0a: {
                            byte[] bytesRead = byteArrayOutputStream.toByteArray();

                            byteArrayOutputStream.write(byteRead);

                            return bytesRead;
                        }

                        default: {
                            byteArrayOutputStream.write(byteRead);

                            break;
                        }
                    }

                    break;
                }

                case LF_CR: {
                    if (byteRead == 0x0a) {
                        byte nextByteRead = prologixReadByte(deadlineMillis - currentMillis);

                        if (nextByteRead == 0x0d) {
                            byte[] bytesRead = byteArrayOutputStream.toByteArray();

                            byteArrayOutputStream.write(byteRead);
                            byteArrayOutputStream.write(nextByteRead);

                            return bytesRead;
                        } else {
                            byteArrayOutputStream.write(byteRead);
                            byteArrayOutputStream.write(nextByteRead);

                            throw new IOException();
                        }
                    } else {
                        byteArrayOutputStream.write(byteRead);
                    }

                    break;
                }

                default: {
                    throw new RuntimeException();
                }
            }
        }
    }

    public void prologixClearReadBuffer() {
        List<Byte> drainedBytes = new ArrayList<>();

        this.readBytes.drainTo(drainedBytes);

        if (!drainedBytes.isEmpty()) {
            final byte[] bytes = new byte[drainedBytes.size()];

            int i = 0;

            for (Byte b : drainedBytes) {
                bytes[i++] = b;
            }
        }
    }

    // Prologix Write Raw

    private void prologixWriteRaw(byte[] bytes) throws IOException, IllegalArgumentException {
        if (bytes == null) {
            throw new IllegalArgumentException("ERROR: Invalid Bytes");
        }

        URLConnection prologixURLConnection = this.prologixURLConnection;

        if (prologixURLConnection == null) {
            throw new IOException("ERROR: Invalid URL");
        }

        this.prologixURLConnection.getOutputStream().write(bytes);
    }

    private void prologixWriteRaw(String string) throws IOException, IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("ERROR: Invalid String");
        }

        prologixWriteRaw(string.getBytes(Charset.forName("US-ASCII")));
    }

    // Prologix Write Cooked

    private final static byte lfByte = (byte) 10;
    private final static byte crByte = (byte) 13;
    private final static byte escByte = (byte) 27;
    private final static byte plusByte = (byte) 43;

    private final static byte prologixCommandTerminator = lfByte;

    private byte[] prologixCookString(byte[] bytes) throws IllegalArgumentException {
        if (bytes == null) {
            throw new IllegalArgumentException("ERROR: Invalid Bytes");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (byte b : bytes) {
            switch (b) {
                case lfByte:
                case crByte:
                case escByte:
                case plusByte: {
                    byteArrayOutputStream.write(escByte);
                }
                default: {
                    byteArrayOutputStream.write(b);
                }
            }
        }

        byteArrayOutputStream.write(prologixCommandTerminator);

        return byteArrayOutputStream.toByteArray();
    }

    public void prologixWriteCooked(byte[] bytes) throws IOException, IllegalArgumentException {
        prologixWriteRaw(prologixCookString(bytes));
    }

    public void prologixWriteCooked(String string) throws IOException, IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("ERROR: Invalid String");
        }
    }

    // Prologix Write Command

    public void prologixWriteCommand(String command) throws IOException, IllegalArgumentException {
        if (command == null || command.length() < 2) {
            throw new IllegalArgumentException("ERROR: Invalid Command");
        }

        if (!command.startsWith("++")) {
            throw new IllegalArgumentException("ERROR: Invalid Command");
        }

        prologixWriteRaw(command
                + new String(new byte[] {prologixCommandTerminator}, Charset.forName("US-ASCII")));
    }

    // Prologix Commands

    public String getBusAddress() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(addressCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setBusAddress(BusAddress busAddress) throws IOException {
        prologixWriteCommand(addressCommand + "" + busAddress);
    }

    public String getAuto() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(autoCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setAuto(int value) throws IOException {
        prologixWriteCommand(autoCommand + "" + value);
    }

    public void sendClear() throws IOException {
        prologixWriteCommand(clearCommand);
    }

    public String getEOI() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(eoiCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setEOI(int value) throws IOException {
        prologixWriteCommand(eoiCommand + "" + value);
    }

    public String getEOS() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(eosCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setEOS(int value) throws IOException {
        prologixWriteCommand(eosCommand + "" + value);
    }

    public String getEOTEnable() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(eotEnableCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setEOTEnable(int value) throws IOException {
        prologixWriteCommand(eotEnableCommand + "" + value);
    }

    public String getEOTChar() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(eotCharCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setEOTChar(int value) throws IOException {
        prologixWriteCommand(eotCharCommand + "" + value);
    }

    public void sendIFC() throws IOException {
        prologixWriteCommand(ifcCommand);
    }

    public void sendLLO() throws IOException {
        prologixWriteCommand(lloCommand);
    }

    public void sendLOC() throws IOException {
        prologixWriteCommand(locCommand);
    }

    public String getLON() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(lonCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setLON(int value) throws IOException {
        prologixWriteCommand(lonCommand + "" + value);
    }

    public String getMode() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(modeCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setMode(int value) throws IOException {
        prologixWriteCommand(modeCommand + "" + value);
    }

    public String getRead() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(readCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    // Look into how to do read command

    public void setReadTimeout(int value) throws IOException {
        prologixWriteCommand(readTmoMsCommand + "" + value);
    }

    public void sendReset() throws IOException {
        prologixWriteCommand(resetCommand);
    }

    public String getSaveConfig() throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(saveConfigCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void setSaveConfig(int value) throws IOException {
        prologixWriteCommand(saveConfigCommand + "" + value);
    }

    public String sendSpoll() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(spollCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public String sendSpoll(BusAddress busAddress) throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(spollCommand + "" + busAddress);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public String getSRQ() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(srqCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public String getStatus() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(statusCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public void sendStatus(int value) throws IOException {
        prologixWriteCommand(statusCommand + "" + value);
    }

    // Look into how to do trg command

    public String getVersion() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(versionCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }

    public String getHelp() throws IOException, InterruptedException, TimeoutException {
        prologixWriteCommand(helpCommand);
        prologixClearReadBuffer();
        return prologixReadLine(ReadlineTerminationMode.LF, 10).toString();
    }
}
