package gpiblib;

public class SystemBusType {
    private String systemBusType;

    public String[] systemBusTypes = {"Prologix USB", "Prologix Ethernet"};

    public String getSystemBusType() {
        return systemBusType;
    }

    public void setSystemBusType(String systemBusType) {
        this.systemBusType = systemBusType;
    }
}
