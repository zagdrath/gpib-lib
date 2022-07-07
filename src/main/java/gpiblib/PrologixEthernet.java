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

import java.net.Socket;
import java.io.IOException;

public class PrologixEthernet {
    private String ipAddress;
    private int tcpPort;

    /**
     * Constructs and configures the Prologix Ethernet.
     * 
     * @param ipAddress
     * @param tcpPort
     */
    public PrologixEthernet(String ipAddress, int tcpPort) {
        if (ipAddress == null || ipAddress.trim().isBlank()) {
            throw new IllegalArgumentException("ERROR: Invalid IP Address");
        } else {
            this.ipAddress = ipAddress.trim();
        }

        if (tcpPort < 0 || tcpPort > 65535) {
            throw new IllegalArgumentException("ERROR: Invalid TCP Port");
        } else {
            this.tcpPort = tcpPort;
        }

        openConnection(ipAddress, tcpPort);
    }

    // Addressing

    /** 
     * Get's the IP address.
     * 
     * @return IP address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set's the IP address.
     * 
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get's the TCP port.
     * 
     * @return TCP port
     */
    public int getTcpPort() {
        return tcpPort;
    }

    /**
     * Set's the TCP port.
     * 
     * @param tcpPort
     */
    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    // Socket Connections

    private Socket socket;

    /**
     * Opens the network connection to the Prologix Ethernet.
     * 
     * @param ipAddress
     * @param tcpPort
     */
    public void openConnection(String ipAddress, int tcpPort) {
        try {
            socket = new Socket(ipAddress, tcpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the network connection to the Prologix Ethernet.
     */
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
