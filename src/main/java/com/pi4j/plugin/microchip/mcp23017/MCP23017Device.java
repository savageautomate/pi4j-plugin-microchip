package com.pi4j.plugin.microchip.mcp23017;

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.exception.IONotFoundException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipGpioDevice;

public class MCP23017Device implements MCP23017, MicrochipGpioDevice {


    protected final I2C i2c;
    protected byte currentStatesA = 0;
    protected byte currentStatesB = 0;
    protected byte currentDirectionA = 0;
    protected byte currentDirectionB = 0;
    protected byte currentPullupA = 0;
    protected byte currentPullupB = 0;

    public MCP23017Device(I2C i2c){
        // set local reference to I2C instance
        this.i2c = i2c;

        // read initial GPIO pin states
        currentStatesA = i2c.readRegisterByte(REGISTER_GPIO_A);
        currentStatesB = i2c.readRegisterByte(REGISTER_GPIO_B);

        // set all default pins directions
        i2c.write(REGISTER_IODIR_A, currentDirectionA);
        i2c.write(REGISTER_IODIR_B, currentDirectionB);

        // set all default pin interrupts
        i2c.write(REGISTER_GPINTEN_A, currentDirectionA);
        i2c.write(REGISTER_GPINTEN_B, currentDirectionB);

        // set all default pin interrupt default values
        i2c.write(REGISTER_DEFVAL_A, (byte) 0x00);
        i2c.write(REGISTER_DEFVAL_B, (byte) 0x00);

        // set all default pin interrupt comparison behaviors
        i2c.write(REGISTER_INTCON_A, (byte) 0x00);
        i2c.write(REGISTER_INTCON_B, (byte) 0x00);

        // set all default pin states
        i2c.write(REGISTER_GPIO_A, currentStatesA);
        i2c.write(REGISTER_GPIO_B, currentStatesB);

        // set all default pin pull up resistors
        i2c.write(REGISTER_GPPU_A, currentPullupA);
        i2c.write(REGISTER_GPPU_B, currentPullupB);
    }

    @Override
    public void initialize(Digital io){
        if(io.address().intValue() < GPIO_B_OFFSET){
            setModeA(io);
        }
        else if(io.address().intValue() >= GPIO_B_OFFSET){
            setModeB(io);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    @Override
    public DigitalState state(Digital io) {
        if(io.address().intValue() < GPIO_B_OFFSET){
            return getStateA(io);
        }
        else{
            return getStateB(io);
        }
    }

    @Override
    public void state(Digital io, DigitalState state) throws IOException {
        if(io.address().intValue() < GPIO_B_OFFSET){
            setStateA(io, state);
        }
        else{
            setStateB(io, state);
        }
    }

    @Override
    public void pull(Digital io, PullResistance resistance) {
        try {
            // determine A or B port based on pin address
            if (io.address().intValue() < GPIO_B_OFFSET) {
                setPullResistanceA(io, resistance);
            } else {
                setPullResistanceB(io, resistance);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void shutdown(Digital io, Context context) throws ShutdownException {
        // TODO :: Implement GPIO OUTPUT pin shutdown logic
    }

    private void setModeA(Digital io) {
        // determine register and pin address
        int pin = io.address().intValue() - GPIO_A_OFFSET;

        // determine update direction value based on mode
        if (io instanceof DigitalInput) {
            currentDirectionA |= pin;
        } else if (io instanceof DigitalOutput) {
            currentDirectionA &= ~pin;
        }

        // next update direction value
        i2c.write(REGISTER_IODIR_A, currentDirectionA);

        // enable interrupts; interrupt on any change from previous state
        i2c.write(REGISTER_GPINTEN_A, currentDirectionA);
    }

    private void setModeB(Digital io) {
        // determine register and pin address
        int pin = io.address().intValue() - GPIO_B_OFFSET;

        // determine update direction value based on mode
        if (io instanceof DigitalInput) {
            currentDirectionB |= pin;
        } else if (io instanceof DigitalOutput) {
            currentDirectionB &= ~pin;
        }

        // next update direction (mode) value
        i2c.write(REGISTER_IODIR_B, currentDirectionB);

        // enable interrupts; interrupt on any change from previous state
        i2c.write(REGISTER_GPINTEN_B, currentDirectionB);
    }

    private DigitalState getStateA(Digital io){

        // determine pin address
        int pin = io.address().intValue() - GPIO_A_OFFSET;

        // determine and return pin state
        return (currentStatesA & pin) == pin ? DigitalState.HIGH : DigitalState.LOW;
    }

    private DigitalState getStateB(Digital io){

        // determine pin address
        int pin = io.address().intValue() - GPIO_B_OFFSET;

        // determine and return pin state
        return (currentStatesB & pin) == pin ? DigitalState.HIGH : DigitalState.LOW;
    }

    private void setStateA(Digital io, DigitalState state) throws IOException {
        // determine pin address
        int pin = io.address().intValue() - GPIO_A_OFFSET;

        // determine state value for pin bit
        if (state.isHigh()) {
            currentStatesA |= pin;
        } else {
            currentStatesA &= ~pin;
        }

        // update state value
        i2c.write(REGISTER_GPIO_A, currentStatesA);
    }

    private void setStateB(Digital io, DigitalState state) throws IOException {
        // determine pin address
        int pin = io.address().intValue() - GPIO_B_OFFSET;

        // determine state value for pin bit
        if (state.isHigh()) {
            currentStatesB |= pin;
        } else {
            currentStatesB &= ~pin;
        }

        // update state value
        i2c.write(REGISTER_GPIO_B, currentStatesB);
    }


    private void setPullResistanceA(Digital io, PullResistance resistance) throws IOException {
        // determine pin address
        int pin = io.address().intValue() - GPIO_A_OFFSET;

        // determine pull up value for pin bit
        if (resistance == PullResistance.PULL_UP) {
            currentPullupA |= pin;
        } else {
            currentPullupA &= ~pin;
        }

        // next update pull up resistor value
        i2c.write(REGISTER_GPPU_A, currentPullupA);
    }

    private void setPullResistanceB(Digital io, PullResistance resistance) throws IOException {
        // determine pin address
        int pinAddress = io.address().intValue() - GPIO_B_OFFSET;

        // determine pull up value for pin bit
        if (resistance == PullResistance.PULL_UP) {
            currentPullupB |= pinAddress;
        } else {
            currentPullupB &= ~pinAddress;
        }

        // next update pull up resistor value
        i2c.write(REGISTER_GPPU_B, currentPullupB);
    }

}
