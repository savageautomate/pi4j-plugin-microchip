package com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.test;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.microchip.mcp23017.MCP23017;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalInputProvider;
import com.pi4j.plugin.microchip.mcp23017.provider.gpio.digital.MCP23017DigitalOutputProvider;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;

public class MCP23017GpioOutputExample {

    private static final int PIN_LED = MCP23017.GPA1;
    private static final int MCP23017_I2C_ADDRESS = MCP23017.DEFAULT_ADDRESS;
    private static final int MCP23017_I2C_BUS = 1;

    /**
     * Sample application using MCP23017 GPIO expansion chip.
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
                MockI2CProvider.newInstance(),
                MCP23017DigitalOutputProvider.newInstance(),
                MCP23017DigitalInputProvider.newInstance()).build();

        // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        // create I2C instance for communication with MCP23017
        I2C mcp23017_i2c = pi4j.i2c().create(MCP23017_I2C_BUS, MCP23017_I2C_ADDRESS);

        // create some fake data for the MOCK I2C
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPIO_A, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPIO_B, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPPU_A, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPPU_B, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPINTEN_A, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_GPINTEN_B, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_IODIR_A, 0x00);
        mcp23017_i2c.writeRegister(MCP23017.REGISTER_IODIR_B, 0x00);
        Thread.sleep(100);

        // if we don't have an immediate reference to the actual provider,
        // we can obtain it from the Pi4J context using it's ID string
        MCP23017DigitalOutputProvider provider = pi4j.provider(MCP23017DigitalOutputProvider.ID);

        // TODO :: we need to configure the MCP23017 provider with the necessary I2C instance
        provider.setup(mcp23017_i2c);

        System.out.println("----------------------------------------------------------");
        System.out.println("CREATE GPIO OUTPUT PIN");
        System.out.println("----------------------------------------------------------");
        Thread.sleep(50);

        // Here we will create I/O interfaces for a (GPIO) digital output
        // pin. We will use the MCP23017 digital output provider
        var ledConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .name("LED Flasher")
                .address(PIN_LED)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .build();

        // create the LED output GPIO pin instance
        var led = provider.create(ledConfig);
        System.out.println("----> GPIO CURRENT STATE: " + led.state());

        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J I/O REGISTRY");
        System.out.println("----------------------------------------------------------");
        pi4j.registry().describe().print(System.out);

        // turn LED to the HIGH state
        System.out.println("----------------------------------------------------------");
        System.out.println("SET GPIO PIN TO HIGH STATE");
        System.out.println("----------------------------------------------------------");
        Thread.sleep(50);
        led.high();
        System.out.println("----> GPIO CURRENT STATE: " + led.state());

        System.out.println("----------------------------------------------------------");
        System.out.println("SLEEPING FOR 5 SECONDS");
        System.out.println("----------------------------------------------------------");
        for(int index = 0; index < 100; index++)
            Thread.sleep(50);

        System.out.println("----------------------------------------------------------");
        System.out.println("SHUTDOWN");
        System.out.println("----------------------------------------------------------");
        Thread.sleep(50);
        pi4j.shutdown();
    }
}
