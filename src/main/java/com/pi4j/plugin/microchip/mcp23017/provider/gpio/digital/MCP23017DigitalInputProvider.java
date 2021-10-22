package com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockDigitalInputProvider.java
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

import com.pi4j.plugin.microchip.MicrochipPlugin;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipDigitalInputProvider;

/**
 * <p>MockDigitalInputProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface MCP23017DigitalInputProvider extends MicrochipDigitalInputProvider {
    String NAME = MicrochipPlugin.MCP23017_DIGITAL_INPUT_PROVIDER_NAME;
    String ID = MicrochipPlugin.MCP23017_DIGITAL_INPUT_PROVIDER_ID;

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link MCP23017DigitalInputProvider} object.
     */
    static MCP23017DigitalInputProvider newInstance() {
        return new MCP23017DigitalInputProviderImpl();
    }
}
