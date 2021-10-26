/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  module-info.java
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
module com.pi4j.plugin.pigpio {
    requires com.pi4j;
    requires org.slf4j;

    uses com.pi4j.extension.Plugin;

    exports com.pi4j.plugin.microchip;
    exports com.pi4j.plugin.microchip.provider.gpio.digital;
    exports com.pi4j.plugin.microchip.mcp23017;
    exports com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital;

    provides com.pi4j.extension.Plugin
            with com.pi4j.plugin.microchip.MicrochipPlugin;
}
