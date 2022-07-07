/*
 * @(#)BusAddress.java
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

public class BusAddress {
    private String busAddressURL;
    private byte busAddressPrimary;
    private byte busAddressSecondary;

    private final static byte minNonZeroSecondary = (byte) 0x60;
    private final static byte maxNonZeroSecondary = (byte) 0x7e;

    public BusAddress(byte busAddressPrimary, byte busAddressSecondary) {
        if (busAddressPrimary < 0 || busAddressPrimary > 30) {
            throw new IllegalArgumentException("ERROR: Invalid Primary Bus Address");
        } else {
            this.busAddressPrimary = busAddressPrimary;
        }

        if (busAddressSecondary != 0
                && (busAddressSecondary < minNonZeroSecondary || busAddressSecondary > maxNonZeroSecondary)) {
            throw new IllegalArgumentException("ERROR: Invalid Secondary Bus Address");
        } else {
            this.busAddressSecondary = busAddressSecondary;
        }
    }

    public BusAddress(byte busAddressPrimary) {
        this(busAddressPrimary, (byte) 0);
    }

    public BusAddress fromBusAddressURL(String busAddressURL) {
        if (busAddressURL == null || !busAddressURL.trim().toLowerCase().startsWith("GPIB:")) {
            return null;
        }

        String busAddressPrimaryString;
        String busAddressSecondaryString;
        String busAddresString = busAddressURL.trim().replaceFirst(".*:", "");

        if (busAddresString.contains(",")) {
            busAddressPrimaryString = busAddresString.replaceFirst(",.*", "");
            busAddressSecondaryString = busAddresString.replaceFirst(".*,", "");
        } else {
            busAddressPrimaryString = busAddresString;
            busAddressSecondaryString = null;
        }

        try {
            int busAddressPrimary = Integer.parseInt(busAddressPrimaryString);

            if (busAddressPrimary < 0 || busAddressPrimary > 30) {
                return null;
            }

            if (busAddressSecondaryString != null) {
                int busAddressSecondary = Integer.parseInt(busAddressSecondaryString);

                if (busAddressSecondary != 0
                        && (busAddressSecondary < minNonZeroSecondary || busAddressSecondary > maxNonZeroSecondary)) {
                    return null;
                }

                return new BusAddress((byte) busAddressPrimary, (byte) busAddressSecondary);
            } else {
                return new BusAddress((byte) busAddressPrimary);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();

            return null;

        }
    }

    public byte getBusAddressPrimary() {
        return busAddressPrimary;
    }

    public void setBusAddressPrimary(byte busAddressPrimary) {
        this.busAddressPrimary = busAddressPrimary;
    }

    public byte getBusAddressSecondary() {
        return busAddressSecondary;
    }

    public void setBusAddressSecondary(byte busAddressSecondary) {
        this.busAddressSecondary = busAddressSecondary;
    }

    public boolean hasBusAddressSecondary() {
        return this.busAddressSecondary != 0;
    }

    public String getBusAddressURL() {
        if (hasBusAddressSecondary()) {
            busAddressURL = "GPIB:" + this.busAddressPrimary + "," + this.busAddressSecondary;
        } else {
            busAddressURL = "GPIB:" + this.busAddressPrimary;
        }

        return busAddressURL;
    }
}
