package com.pi4j.plugin.microchip.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockDigitalOutput.java
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
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.*;


/**
 * <p>MicrochipDigitalOutput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MicrochipDigitalOutput extends DigitalOutputBase implements DigitalOutput {
    /**
     * <p>Constructor for MicrochipDigitalOutput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalOutputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalOutputConfig} object.
     */
    public MicrochipDigitalOutput(MicrochipDigitalOutputProvider provider, DigitalOutputConfig config){
        super(provider, config);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput initialize(Context context) throws InitializeException {
        super.initialize(context);

        // TODO :: Implement GPIO OUTPUT pin initialization
        // TODO :: Implement GPIO OUTPUT pin initial state
        // TODO :: Implement GPIO OUTPUT pin mode

        // configure GPIO pin as an OUTPUT pin
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput state(DigitalState state) throws IOException {
        // TODO :: Implement GPIO OUTPUT pin state instruction request
        return super.state(state);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput shutdown(Context context) throws ShutdownException {
        // TODO :: Implement GPIO OUTPUT pin shutdown logic
        return super.shutdown(context);
    }
}
