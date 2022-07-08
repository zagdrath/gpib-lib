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

public class Instrument {
    private String instrumentName;
    private BusAddress busAddress;

    public Instrument(String instrumentName, BusAddress busAddress) {
        this.instrumentName = instrumentName;
        this.busAddress = busAddress;
    }

    public void getInstrumentName() {

    }

    public void setInstrumentName() {

    }

    public void getBusAddress() {

    }

    public void setBusAddress() {
        
    }

    public void getPoweredOn() {

    }

    public void setPoweredOn() {

    }
}
