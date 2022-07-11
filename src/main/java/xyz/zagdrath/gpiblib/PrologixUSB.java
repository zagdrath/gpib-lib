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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.fazecast.jSerialComm.SerialPort;

public class PrologixUSB {
    private SerialPort commPort;

    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    public PrologixUSB(String serialPort) {
        if (serialPort == null) {
            throw new IllegalArgumentException("ERROR: Invalid Serial Port");
        } else {
            commPort = SerialPort.getCommPort(serialPort);
        }

        openConnection(commPort);

        defaultConfig(commPort, 115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
    }

    private void defaultConfig(SerialPort serialPort, int baudRate, int dataBits, int stopBits, int parity) {
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
    }

    public void openConnection(SerialPort serialPort) {
        serialPort.openPort();

        inputStream = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(serialPort.getOutputStream()));
    }

    public void closeConnection(SerialPort serialPort) {
        serialPort.closePort();
    }
}
