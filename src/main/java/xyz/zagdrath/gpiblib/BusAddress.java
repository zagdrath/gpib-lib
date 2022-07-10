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
 * Updated: July 10, 2022
 */

package xyz.zagdrath.gpiblib;

public class BusAddress {
    private String busAddressURL;
    private byte busAddressPrimary;
    private byte busAddressSecondary;

    private final static byte minNonZeroSecondary = (byte) 0x60;
    private final static byte maxNonZeroSecondary = (byte) 0x7e;

    /**
     * Factory constructor to create new instances of a BusAddress.
     * 
     * @param busAddressPrimary
     * @param busAddressSecondary
     * @throws IllegalArgumentException
     */
    public BusAddress(byte busAddressPrimary, byte busAddressSecondary) throws IllegalArgumentException {
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

    /**
     * Factory constructor to create new instances of a BusAddress.
     * 
     * @param busAddressPrimary
     */
    public BusAddress(byte busAddressPrimary) {
        this(busAddressPrimary, (byte) 0);
    }

    /**
     * Converts a URL to a BusAddress.
     * 
     * @param busAddressURL
     * @return BusAddress
     */
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

    /**
     * Get's the primary bus address.
     * 
     * @return busAddressPrimary
     */
    public byte getBusAddressPrimary() {
        return busAddressPrimary;
    }

    /**
     * Set's the primary bus address.
     * 
     * @param busAddressPrimary
     */
    public void setBusAddressPrimary(byte busAddressPrimary) {
        this.busAddressPrimary = busAddressPrimary;
    }

    /**
     * Get's the secondary bus address.
     * 
     * @return busAddressSecondary
     */
    public byte getBusAddressSecondary() {
        return busAddressSecondary;
    }

    /**
     * Set's the secondary bus address.
     * 
     * @param busAddressSecondary
     */
    public void setBusAddressSecondary(byte busAddressSecondary) {
        this.busAddressSecondary = busAddressSecondary;
    }

    /**
     * Check's for secondary bus address.
     * 
     * @return
     */
    public boolean hasBusAddressSecondary() {
        return this.busAddressSecondary != 0;
    }

    /**
     * Get's the URL.
     * 
     * @return busAddressURL
     */
    public String getBusAddressURL() {
        if (hasBusAddressSecondary()) {
            busAddressURL = "GPIB:" + this.busAddressPrimary + "," + this.busAddressSecondary;
        } else {
            busAddressURL = "GPIB:" + this.busAddressPrimary;
        }

        return busAddressURL;
    }
}
