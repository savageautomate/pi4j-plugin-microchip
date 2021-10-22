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
    private final int pin;
    private DigitalState state = DigitalState.LOW;

    /**
     * <p>Constructor for MicrochipDigitalInput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalInputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalInputConfig} object.
     */
    public MicrochipDigitalInput(MicrochipDigitalInputProvider provider, DigitalInputConfig config){
        super(provider, config);
        this.pin = config.address().intValue(); // obtain PIN numerical address from config
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput initialize(Context context) throws InitializeException {
        super.initialize(context);

        // TODO :: Implement GPIO INPUT pin initialization
        // TODO :: Implement GPIO INPUT pin initial state
        // TODO :: Implement GPIO INPUT pin pull resistance
        // TODO :: Implement GPIO INPUT pin mode
        // TODO :: Implement GPIO INPUT pin debounce

        // if configured, set GPIO pin pull resistance
//        switch(config.pull()){
//            case PULL_DOWN:{
//                break;
//            }
//            case PULL_UP:{
//                break;
//            }
//        }

        // if configured, set GPIO debounce
//        if(this.config.debounce() != null) {
//        }

        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState state() {
        try {
            // TODO :: Implement GPIO INPUT pin state query
            return this.state;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return DigitalState.UNKNOWN;
        }
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput shutdown(Context context) throws ShutdownException {
        // TODO :: Implement GPIO INPUT pin shutdown logic
        return super.shutdown(context);
    }
}
