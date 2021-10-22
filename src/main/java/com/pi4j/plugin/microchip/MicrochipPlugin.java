package com.pi4j.plugin.microchip;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPlugin.java
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
import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalInputProvider;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalOutputProvider;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipDigitalInputProvider;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipDigitalOutputProvider;
import com.pi4j.provider.Provider;

/**
 * <p>MicrochipPlugin class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MicrochipPlugin implements Plugin {

    public static final String NAME = "Microchip";
    public static final String ID = "microchip";

    // specific providers
    public static final String MCP23017_NAME = "MCP23017";
    public static final String MCP23017_ID = "mcp23017";

    // Digital Input (GPIO) Provider name and unique ID
    public static final String MCP23017_DIGITAL_INPUT_PROVIDER_NAME = NAME +  " Digital Input (GPIO) Provider";
    public static final String MCP23017_DIGITAL_INPUT_PROVIDER_ID = ID + "-digital-input";

    // Digital Output (GPIO) Provider name and unique ID
    public static final String DIGITAL_OUTPUT_PROVIDER_NAME = NAME +  " Digital Output (GPIO) Provider";
    public static final String DIGITAL_OUTPUT_PROVIDER_ID = ID + "-digital-output";

    /** {@inheritDoc} */
    @Override
    public void initialize(PluginService service) throws InitializeException {

        // create new instances of the PIGPIO plugin I/O providers using the newly created PIGPIO lib reference
        Provider providers[] = {
                MCP23017DigitalInputProvider.newInstance(),
                MCP23017DigitalOutputProvider.newInstance(),
        };

        // register all PiGpio I/O Providers with the plugin service
        service.register(providers);
    }

    /** {@inheritDoc} */
    @Override
    public void shutdown(Context context) throws ShutdownException {
        // perform shutdown on hardware
    }
}
