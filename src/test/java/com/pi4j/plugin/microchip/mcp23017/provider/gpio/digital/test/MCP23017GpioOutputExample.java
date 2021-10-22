package com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.test;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalInputProvider;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalOutputProvider;

public class MCP23017GpioOutputExample {

    private static final int PIN_LED = 1;

    /**
     * This application blinks a led and counts the number the button is pressed. The blink speed increases with each
     * button press, and after 5 presses the application finishes.
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {


        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        var pi4j = Pi4J.newContextBuilder().add(
                MCP23017DigitalOutputProvider.newInstance(),
                MCP23017DigitalInputProvider.newInstance()).build();

        // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        // if we don't have an immediate reference to the actual provider,
        // we can obtain it from the Pi4J context using it's ID string
        MCP23017DigitalOutputProvider provider = pi4j.provider(MCP23017DigitalOutputProvider.ID);

        // TODO :: we need to configure the MCP23017 provider with the necessary SPI/I2C config and any other startup configs
        //provider.configure();

        // Here we will create I/O interfaces for a (GPIO) digital output
        // pin. We will use the MCP23017 digital output provider
        var ledConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .name("LED Flasher")
                .address(PIN_LED)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider(MCP23017DigitalOutputProvider.ID);

        // create the LED output GPIO pin instance
        var led = pi4j.create(ledConfig);

        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J I/O REGISTRY");
        System.out.println("----------------------------------------------------------");
        pi4j.registry().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        // turn LED to the HIGH state
        led.high();
        Thread.sleep(2000);
    }
}
