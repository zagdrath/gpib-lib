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
import java.io.IOException;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.script.ScriptException;
import java.net.UnknownServiceException;

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
    public PrologixEthernet(URL prologixURL) throws IllegalArgumentException, ScriptException {
        if (prologixURL == null) {
            throw new IllegalArgumentException("ERROR: Invalid URL");
        } else {
            this.prologixURL = prologixURL;
        }

        openConnection(prologixURL);
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

            inputStream = new BufferedReader(new InputStreamReader(prologixURLConnection.getInputStream()));
            outputStream = new BufferedWriter(new OutputStreamWriter(prologixURLConnection.getOutputStream()));
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

    private final String addressCommand = "++addr";
    private final String autoCommand = "++auto";
    private final String clearCommand = "++clr";
    private final String eoiCommand = "++eoi";
    private final String eosCommand = "++eos";
    private final String eotEnableCommand = "++eot_enable";
    private final String eotCharCommand = "++eot_char";
    private final String ifcCommand = "++ifc";
    private final String lloCommand = "++llo";
    private final String locCommand = "++loc";
    private final String lonCommand = "++lon";
    private final String modeCommand = "++mode";
    private final String readCommand = "++read";
    private final String readTmoMsCommand = "++read_tmo_ms";
    private final String resetCommand = "++rst";
    private final String saveConfigCommand = "++savecfg";
    private final String spollCommand = "++spoll";
    private final String srqCommand = "++srq";
    private final String statusCommand = "++status";
    private final String triggerCommand = "++trg";
    private final String versionCommand = "++ver";
    private final String helpCommand = "++help";
}
