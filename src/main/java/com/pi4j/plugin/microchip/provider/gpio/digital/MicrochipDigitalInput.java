package com.pi4j.plugin.microchip.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockDigitalInput.java
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


import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.gpio.digital.*;

/**
 * <p>MicrochipDigitalInput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MicrochipDigitalInput extends DigitalInputBase implements DigitalInput {
    protected final MicrochipGpioDevice device;

    /**
     * <p>Constructor for MicrochipDigitalInput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalInputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalInputConfig} object.
     */
    public MicrochipDigitalInput(MicrochipDigitalInputProvider provider, MicrochipGpioDevice device, DigitalInputConfig config){
        super(provider, config);
        this.device = device;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput initialize(Context context) throws InitializeException {
        super.initialize(context);

        // GPIO INPUT pin initialization
        // initialize this I/O instance with the underlying hardware chip
        // perform any communication with the hardware to configure this chip for INPUT
        device.initialize(this);

        // configure GPIO INPUT pin pull resistance on the underlying hardware chip
        if(config.pull() != PullResistance.OFF) {
            device.pull(this, config.pull());
        }

        // if configured, set GPIO debounce
        if(this.config.debounce() != null) {
            // TODO :: Implement GPIO INPUT pin debounce
        }

        // return this IO instance
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState state() {
        // get IO instance digital state from underlying hardware chip
        // return this IO instance
        return device.state(this);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput shutdown(Context context) throws ShutdownException {
        // perform any shutdown required
        super.shutdown(context);

        // perform any shutdown required on the hardware chip
        device.shutdown(this, context);

        // return this IO instance
        return this;
    }
}
