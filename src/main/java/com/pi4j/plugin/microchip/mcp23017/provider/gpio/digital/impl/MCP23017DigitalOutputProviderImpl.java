package com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockDigitalOutputProviderImpl.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.microchip.mcp23017.internal.MCP23017Device;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalOutputProvider;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipDigitalOutput;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipDigitalOutputProviderBase;
import com.pi4j.provider.exception.ProviderException;

/**
 * <p>MockDigitalOutputProviderImpl class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MCP23017DigitalOutputProviderImpl extends MicrochipDigitalOutputProviderBase implements MCP23017DigitalOutputProvider {

    protected MCP23017Device device = null;

    /**
     * <p>Constructor for MockDigitalOutputProviderImpl.</p>
     */
    public MCP23017DigitalOutputProviderImpl(){
        super(ID, NAME);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput create(DigitalOutputConfig config) {

        // validate provider setup
        if(this.device == null){
            throw new ProviderException("MCP23017DigitalOutputProvider::setup(...) has not been called; this provider must be configured using the setup() method prior to creating I/O instances.");
        }

        // create new output I/O instance
        return new MicrochipDigitalOutput(this, device, config);
    }

    @Override
    public void setup(Object... args) throws ProviderException {

        // validate arguments exists
        if(args == null || args.length <= 0){
            throw new ProviderException("MCP23017DigitalOutputProvider::setup(...) is missing required arguments parameter.");
        }

        // validate first argument is of type I2C
        if(!(args[0] instanceof I2C)){
            throw new ProviderException("MCP23017DigitalOutputProvider::setup(...) is missing required I2C parameter.");
        }

        // validate first call to setup
        if(this.device != null){
            throw new ProviderException("MCP23017DigitalOutputProvider::setup(...) has already been called; this method is not reentrant.");
        }

        // create RAW device to communicate with chip via I2C instance
        this.device = new MCP23017Device ((I2C) args[0]);
    }

    @Override
    public void setup(I2C i2c) throws ProviderException {
        Object[] args = new Object[] { i2c };
        this.setup(args);
    }
}
