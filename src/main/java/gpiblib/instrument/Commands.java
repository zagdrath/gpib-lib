/*
 * @(#)Commands.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 07, 2022
 * Updated: July 07, 2022
 */

package gpiblib.instrument;

public class Commands {
    public class HP5334Commands {
        public final static String inputACouplingDC = "AA0";
        public final static String inputACouplingAC = "AA1";
        public final static String inputASlopePos = "AS0";
        public final static String inputASlopeNeg = "AS1";
        public final static String channelATrigLevel = "AT";
        public final static String autoTrigOff = "AU0";
        public final static String autoTrigOn = "AU1";
        public final static String inputAAttnX1 = "AX0";
        public final static String inputAAttnX10 = "AX1";
        public final static String inputAImpedance1M = "AZ0";
        public final static String inputAImpedence50 = "AX1";
        public final static String inputBCouplingDC = "BA0";
        public final static String inputBCouplingAC = "BA1";
        public final static String inputBSlopePos = "BS0";
        public final static String inputBSlopeNeg = "BS1";
        public final static String channelBTrigLevel = "BT";
        public final static String inputBAttnX1 = "BX0";
        public final static String inputBAttnX10 = "BX1";
        public final static String inputBImpedance1M = "BZ0";
        public final static String inputBImpedence50 = "BX1";
        public final static String comInputsOff = "CO0";
        public final static String comInputsOn = "CC1";
        public final static String inputFilterOff = "FI0";
        public final static String inputFilterOn = "FI1";
        public final static String sensModeOff = "SE0";
        public final static String sensModeOn = "SE1";
        public final static String remoteTrigLevelsOff = "TR0";
        public final static String remoteTrigLevelsOn = "TR1";
        public final static String extStartArmSlopePos = "XA1";
        public final static String extStartArmOff = "XA2";
        public final static String extStartArmSlopeNeg = "XA3";
        public final static String extStopArmSlopePos = "XO1";
        public final static String extStopArmOff = "XO2";
        public final static String extStopArmSlopeNeg = "XO3";
        public final static String freqA = "FN1";
        public final static String freqB = "FN2";
        public final static String freqC = "FN3";
        public final static String periodA = "FN4";
        public final static String timeIntervalAToB = "FN5";
        public final static String timeIntervalAToBDelay = "FN6";
        public final static String ratioAB = "FN7";
        public final static String totStopA = "FN8";
        public final static String totStartA = "FN9";
        public final static String pulseWidthA = "FN10";
        public final static String riseFallTimeA = "FN11";
        public final static String voltMode = "FN12";
        public final static String readTrigLevels = "FN13";
        public final static String readPeaksA = "FN14";
        public final static String readPeaksB = "FN15";
        public final static String mathDisableOff = "MD0";
        public final static String mathDisableOn = "MD1";
        public final static String norm = "MN";
        public final static String offset = "MO";
        public final static String recallSetup = "MR";
        public final static String storeSetup = "MS";
        public final static String highSpeedOff = "HS0";
        public final static String highSpeedOn = "HS1";
        public final static String instrumentID = "ID";
        public final static String powerOn = "IN";
        public final static String reset = "RE";
        public final static String srqMask = "SM";
        public final static String transmitCalData = "TC";
        public final static String transmitError = "TE";
        public final static String waitAddressedOff = "WA0";
        public final static String waitAddressedOn = "WA1";
    }
}
