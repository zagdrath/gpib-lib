package xyz.zagdrath.gpiblib;

import java.io.IOException;
import java.net.URL;
import javax.script.ScriptException;

public class SystemBus {
    private String systemBusType;

    private PrologixEthernet prologixEthernet;
    private PrologixUSB prologixUSB;

    public SystemBus(String systemBusType, URL prologixURL, String serialPort)
            throws IllegalArgumentException, IOException, ScriptException {
        this.systemBusType = systemBusType;

        if (systemBusType == "Ethernet") {
            prologixEthernet = new PrologixEthernet(prologixURL);
        } else if (systemBusType == "USB") {
            prologixUSB = new PrologixUSB(serialPort);
        }
    }
}
