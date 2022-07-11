/*
 * @(#)Instrument.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 06, 2022
 * Updated: July 11, 2022
 */

package xyz.zagdrath.gpiblib;

public class Instrument {
    private String instrumentName;
    private BusAddress busAddress;
    private boolean poweredOn;

    public Instrument(String instrumentName, BusAddress busAddress) {
        this.instrumentName = instrumentName;
        this.busAddress = busAddress;
    }

    /**
     * Get's the instrument name.
     * 
     * @return instrumentName
     */
    public String getInstrumentName() {
        return instrumentName;
    }

    /**
     * Set's the instrument name.
     * 
     * @param instrumentName
     */
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    /**
     * Get's the bus address.
     * 
     * @return busAddress
     */
    public BusAddress getBusAddress() {
        return busAddress;
    }

    /**
     * Set's the bus address.
     * 
     * @param busAddress
     */
    public void setBusAddress(BusAddress busAddress) {
        this.busAddress = busAddress;
    }

    public void getPoweredOn() {

    }

    public void setPoweredOn() {

    }
}
