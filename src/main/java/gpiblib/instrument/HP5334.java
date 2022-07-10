/*
 * @(#)HP5334.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 07, 2022
 * Updated: July 08, 2022
 */

package gpiblib.instrument;

import gpiblib.BusAddress;
import gpiblib.Instrument;
import java.io.IOException;
import gpiblib.PrologixEthernet;
import javax.script.ScriptException;
import java.util.concurrent.TimeoutException;

import gpiblib.util.ReadlineTerminationMode;
import gpiblib.instrument.Commands.HP5334Commands;

public class HP5334 extends Instrument {
    private PrologixEthernet prologixEthernet;

    public HP5334(String instrumentName, BusAddress busAddress)
            throws IllegalArgumentException, IOException, ScriptException {
        super(instrumentName, busAddress);

        prologixEthernet = new PrologixEthernet();
    }

    // HP 5334 Commands

    public void setCoupling(String input, String type)
            throws IllegalArgumentException, IOException {
        if (input == "a" || type == "AC") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputACouplingAC);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "a" || type == "DC") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputACouplingDC);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b" || type == "AC") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBCouplingAC);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b" || type == "DC") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBCouplingDC);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }
    }

    public void setSlope(String input, String polarity)
            throws IllegalArgumentException, IOException {
        if (input == "a" || polarity == "positive") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputASlopePos);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "a" || polarity == "negative") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputASlopeNeg);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b" || polarity == "positive") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBSlopePos);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b" || polarity == "positive") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBSlopeNeg);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }
    }

    public void setTriggerLevel(String input, double value)
            throws IllegalArgumentException, IOException {
        if (input == "a") { // TODO: Trigger level limiting +-5.1
            prologixEthernet.prologixWriteCommand(HP5334Commands.channelATrigLevel + value);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b") { // TODO: Trigger level limiting +-5.1
            prologixEthernet.prologixWriteCommand(HP5334Commands.channelBTrigLevel + value);
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }
    }

    public void setAutoTrigger(boolean value) throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.autoTrigOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.autoTrigOn);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }
    }

    public void setAttenuation(String input, int value)
            throws IllegalArgumentException, IOException {
        if (input == "a" || value == 1) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputAAttnX1);
        } else {
            throw new IOException("ERROR: Value Must be 1 or 10");
        }

        if (input == "a" || value == 10) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputAAttnX10);
        } else {
            throw new IOException("ERROR: Value Must be 1 or 10");
        }

        if (input == "b" || value == 1) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBAttnX1);
        } else {
            throw new IOException("ERROR: Value Must be 1 or 10");
        }

        if (input == "b" || value == 10) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBAttnX10);
        } else {
            throw new IOException("ERROR: Value Must be 1 or 10");
        }
    }

    public void setImpedance(String input, int value) throws IllegalArgumentException, IOException {
        if (input == "a" || value == 1) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputAImpedance1M);
        } else {
            throw new IOException("ERROR: Value Must be 1M Ohm or 50 Ohm");
        }

        if (input == "a" || value == 50) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputAImpedence50);
        } else {
            throw new IOException("ERROR: Value Must be 1M Ohm or 50 Ohm");
        }

        if (input == "b" || value == 1) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBImpedance1M);
        } else {
            throw new IOException("ERROR: Value Must be 1M Ohm or 50 Ohm");
        }

        if (input == "b" || value == 50) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputBImpedence50);
        } else {
            throw new IOException("ERROR: Value Must be 1M Ohm or 50 Ohm");
        }
    }

    public void setCommonInputs(boolean value) throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.comInputsOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.comInputsOn);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }
    }

    public void setInputFilter(boolean value) throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputFilterOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.inputFilterOn);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }
    }

    public void setSensitivityMode(boolean value) throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.sensModeOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.sensModeOn);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }
    }

    public void setSensitivityLevels(boolean value) throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.remoteTrigLevelsOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.remoteTrigLevelsOn);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }
    }

    public void setArmStart(boolean value, String polarity)
            throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStartArmOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true && polarity == "positive") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStartArmSlopePos);
        } else {
            throw new IOException(
                    "ERROR: Value Must be True or False & Polarity Must be Positive or Negative");
        }

        if (value == true && polarity == "negative") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStartArmSlopeNeg);
        } else {
            throw new IOException(
                    "ERROR: Value Must be True or False & Polarity Must be Positive or Negative");
        }
    }

    public void setArmStop(boolean value, String polarity)
            throws IllegalArgumentException, IOException {
        if (value == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStopArmOff);
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (value == true && polarity == "positive") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStopArmSlopePos);
        } else {
            throw new IOException(
                    "ERROR: Value Must be True or False & Polarity Must be Positive or Negative");
        }

        if (value == true && polarity == "negative") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.extStopArmSlopeNeg);
        } else {
            throw new IOException(
                    "ERROR: Value Must be True or False & Polarity Must be Positive or Negative");
        }
    }

    private String frequency;

    public String getFrequency(String input)
            throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        if (input == "a") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.freqA);

            prologixEthernet.prologixClearReadBuffer();

            frequency =
                    prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "b") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.freqB);

            prologixEthernet.prologixClearReadBuffer();

            frequency =
                    prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        if (input == "c") {
            prologixEthernet.prologixWriteCommand(HP5334Commands.freqC);

            prologixEthernet.prologixClearReadBuffer();

            frequency =
                    prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
        } else {
            throw new IOException("ERROR: Invalid Configuration");
        }

        return frequency;
    }

    public String getPeriod()
            throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixEthernet.prologixWriteCommand(HP5334Commands.periodA);

        prologixEthernet.prologixClearReadBuffer();

        return prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
    }

    private String timeInterval;

    public String getTimeInterval(boolean delay)
            throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        if (delay == false) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.timeIntervalAToB);

            prologixEthernet.prologixClearReadBuffer();

            timeInterval =
                    prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        if (delay == true) {
            prologixEthernet.prologixWriteCommand(HP5334Commands.timeIntervalAToBDelay);

            prologixEthernet.prologixClearReadBuffer();

            timeInterval =
                    prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
        } else {
            throw new IOException("ERROR: Value Must be True or False");
        }

        return timeInterval;
    }

    public String getRatio()
            throws IllegalArgumentException, IOException, InterruptedException, TimeoutException {
        prologixEthernet.prologixWriteCommand(HP5334Commands.ratioAB);

        prologixEthernet.prologixClearReadBuffer();

        return prologixEthernet.prologixReadLine(ReadlineTerminationMode.CR_LF, 10).toString();
    }
}
