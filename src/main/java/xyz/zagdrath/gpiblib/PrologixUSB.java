/*
 * @(#)PrologixUSB.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 11, 2022
 * Updated: July 11, 2022
 */

package xyz.zagdrath.gpiblib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.fazecast.jSerialComm.SerialPort;

public class PrologixUSB {
    private SerialPort commPort;

    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    /**
     * Constructs and configures the Prologix USB.
     * 
     * @param serialPort
     * @throws IOException
     */
    public PrologixUSB(String serialPort) throws IOException {
        if (serialPort == null) {
            throw new IllegalArgumentException("ERROR: Invalid Serial Port");
        } else {
            commPort = SerialPort.getCommPort(serialPort);
        }

        openConnection(commPort);

        defaultConfig(commPort, 115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
    }

    /**
     * Configures the Prologix USB with default settings.
     * 
     * @param serialPort
     * @param baudRate
     * @param dataBits
     * @param stopBits
     * @param parity
     */
    private void defaultConfig(SerialPort serialPort, int baudRate, int dataBits, int stopBits, int parity) {
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
    }

    /**
     * Get's the serial port.
     * 
     * @return commPort
     */
    public SerialPort getSerialPort() {
        return commPort;
    }

    /**
     * Set's the serial port.
     * 
     * @param serialPort
     */
    public void setSerialPort(SerialPort serialPort) {
        this.commPort = serialPort;
    }

    /**
     * Opens the serial connection to the Prologix USB.
     * 
     * @param serialPort
     */
    public void openConnection(SerialPort serialPort) throws IOException {
        serialPort.openPort();

        inputStream = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(serialPort.getOutputStream()));
    }

    /**
     * Closes the serial connection to the Prologix USB.
     * 
     * @param serialPort
     * @throws IOException
     */
    public void closeConnection(SerialPort serialPort) throws IOException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("ERROR: Could not Close Serial Connection");
        }

        serialPort.closePort();
    }
}
