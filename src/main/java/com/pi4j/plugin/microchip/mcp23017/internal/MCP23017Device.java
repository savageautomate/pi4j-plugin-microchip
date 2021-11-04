package com.pi4j.plugin.microchip.mcp23017.internal;

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.exception.IONotFoundException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.microchip.mcp23017.MCP23017;
import com.pi4j.plugin.microchip.provider.gpio.digital.MicrochipGpioDevice;

public class MCP23017Device implements MCP23017, MicrochipGpioDevice {

    // local/internal I2C reference for communication with hardware chip
    protected final I2C i2c;

    /**
     * Default Constructor
     * @param i2c
     */
    public MCP23017Device(I2C i2c){
        // set local reference to I2C instance
        this.i2c = i2c;

        // atomic operation to configure chip registers
        synchronized (this.i2c) {
            // set all default pin interrupt default values
            this.i2c.writeRegister(REGISTER_DEFVAL_A, (byte) 0x00);
            this.i2c.writeRegister(REGISTER_DEFVAL_B, (byte) 0x00);

            // set all default pin interrupt comparison behaviors
            this.i2c.writeRegister(REGISTER_INTCON_A, (byte) 0x00);
            this.i2c.writeRegister(REGISTER_INTCON_B, (byte) 0x00);
        }
    }

    /**
     * Initialize the I/O pin instance
     * @param io
     */
    @Override
    public void initialize(Digital io){

        // determine [A] or [B] port based on IO instance address [0-7 = port A; 8-15 = port B]
        if (isPortA(io)) {

            // determine pin address for [A] pins
            int pin = pinAddressA(io);

            // update direction and interrupt register values on port [A] on hardware chip
            setPinMode(pin, REGISTER_IODIR_A, REGISTER_GPINTEN_A, io);
        }
        else if (isPortB(io)) {

            // determine pin address for [B] pins
            int pin = pinAddressB(io);

            // update direction and interrupt register values on port [A] on hardware chip
            setPinMode(pin, REGISTER_IODIR_B, REGISTER_GPINTEN_B, io);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    /**
     * Query the current state of the I/O pin instance
     * @param io
     * @return
     */
    @Override
    public DigitalState state(Digital io) {

        // determine [A] or [B] port based on IO instance address [0-7 = port A; 8-15 = port B]
        if (isPortA(io)) {

            // determine pin address for [A] pins
            int pin = pinAddressA(io);

            // get current state from port [A] on hardware chip
            return getPinState(pin, REGISTER_GPIO_A);
        }
        else if (isPortB(io)) {

            // determine pin address for [B] pins
            int pin = pinAddressB(io);

            // get current state from port [B] on hardware chip
            return getPinState(pin, REGISTER_GPIO_B);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    /**
     * Set the current state of the I/O pin instance
     * @param io
     * @param state
     * @throws IOException
     */
    @Override
    public void state(Digital io, DigitalState state) throws IOException {

        // determine [A] or [B] port based on IO instance address [0-7 = port A; 8-15 = port B]
        if (isPortA(io)) {

            // determine pin address for [A] pins
            int pin = pinAddressA(io);

            // update state register value on port [A] on hardware chip
            setPinState(pin, REGISTER_GPIO_A, state);
        }
        else if (isPortB(io)) {

            // determine pin address for [B] pins
            int pin = pinAddressB(io);

            // update state register value on port [B] on hardware chip
            setPinState(pin, REGISTER_GPIO_B, state);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    /**
     * Query the current pull resistance of the I/O pin instance
     * @param io
     * @return
     */
    @Override
    public PullResistance pull(Digital io) {

        // determine [A] or [B] port based on IO instance address [0-7 = port A; 8-15 = port B]
        if (isPortA(io)) {

            // determine pin address for [A] pins
            int pin = pinAddressA(io);

            // get current pull resistance from port [A] on hardware chip
            return getPinPullResistance(pin, REGISTER_GPPU_A);
        }
        else if (isPortB(io)) {

            // determine pin address for [B] pins
            int pin = pinAddressB(io);

            // get current pull resistance from port [B] on hardware chip
            return getPinPullResistance(pin, REGISTER_GPPU_B);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    /**
     * Set the pull resistance of the I/O pin instance
     * @param io
     * @param resistance
     */
    @Override
    public void pull(Digital io, PullResistance resistance) {

        // determine [A] or [B] port based on IO instance address [0-7 = port A; 8-15 = port B]
        if (isPortA(io)) {

            // determine pin address for [A] pins
            int pin = pinAddressA(io);

            // update pull register value on port [A] on hardware chip
            setPinPullResistance(pin, REGISTER_GPPU_A, resistance);
        }
        else if (isPortB(io)) {

            // determine pin address for [B] pins
            int pin = pinAddressB(io);

            // update pull register value on on port [B] hardware chip
            setPinPullResistance(pin, REGISTER_GPPU_B, resistance);
        }
        else{
            throw new IONotFoundException("Unsupported pin address: " + io.toString());
        }
    }

    /**
     * Perform any shutdown steps required for the I/O pin instance
     * @param io
     * @param context
     * @throws ShutdownException
     */
    @Override
    public void shutdown(Digital io, Context context) throws ShutdownException {
        // TODO :: Implement any needed GPIO OUTPUT pin shutdown logic here
    }

    /**
     * Determine if I/O pin instance exists on PORT [A] of the hardware chip
     * @param io
     * @return
     */
    private boolean isPortA(Digital io){
        int address = io.address().intValue();
        return (address >= GPIO_A_OFFSET && address < GPIO_A_OFFSET + GPIO_A_PINCOUNT);
    }

    /**
     * Determine if I/O pin instance exists on PORT [B] of the hardware chip
     * @param io
     * @return
     */
    private boolean isPortB(Digital io){
        int address = io.address().intValue();
        return (address >= GPIO_B_OFFSET && address < GPIO_B_OFFSET + GPIO_B_PINCOUNT);
    }

    /**
     * Translate the I/O pin instance address to a pin address on PORT [A] of the hardware chip
     * @param io
     * @return
     */
    private int pinAddressA(Digital io){
        if(isPortA(io))
            return io.address().intValue() - GPIO_A_OFFSET;
        return -1;
    }

    /**
     * Translate the I/O pin instance address to a pin address on PORT [B] of the hardware chip
     * @param io
     * @return
     */
    private int pinAddressB(Digital io){
        if(isPortB(io))
            return io.address().intValue() - GPIO_B_OFFSET;
        return -1;
    }

    private int pinBit(int pin){
        return pin+1;
    }

    private void setPinMode(int pin, byte directionRegister, byte interruptRegister, Digital io) {

        // atomic operation to query current pin direction & interrupts and then update pin direction & interrupts
        synchronized (this.i2c) {
            // --------------- PIN DIRECTION ---------------

            // get current pin direction register value from hardware
            byte direction = i2c.readRegisterByte(directionRegister);

            // determine update direction value based on mode
            if (io instanceof DigitalInput) {
                direction |= pinBit(pin);   // SET PIN DIRECTION TO INPUT (for digital inputs)
            } else if (io instanceof DigitalOutput) {
                direction &= ~pinBit(pin);  // SET PIN DIRECTION TO OUTPUT (for digital outputs)
            }

            // next update direction value
            i2c.writeRegister(directionRegister, direction);

            // --------------- PIN INTERRUPT ---------------

            // get current pin interrupt register value from hardware
            byte interrupt = i2c.readRegisterByte(interruptRegister);

            // determine update interrupt value based on mode
            if (io instanceof DigitalInput) {
                interrupt |= pinBit(pin);   // ENABLE PIN INTERRUPT (for digital inputs)
            } else if (io instanceof DigitalOutput) {
                interrupt &= ~pinBit(pin);  // DISABLE PIN INTERRUPT (for digital outputs)
            }

            // enable interrupts; interrupt on any change from previous state
            i2c.writeRegister(interruptRegister, interrupt);
        }
    }

    private DigitalState getPinState(int pin, byte register){

        // get current state register values from hardware chip
        byte states = i2c.readRegisterByte(register);

        // determine and return pin state
        return (states & pinBit(pin)) == pinBit(pin) ? DigitalState.HIGH : DigitalState.LOW;
    }

    private void setPinState(int pin, byte register, DigitalState state) throws IOException {
        // atomic operation to query current pin states and then update pin states
        synchronized (this.i2c) {
            // get current state register values from hardware chip
            byte states = i2c.readRegisterByte(register);

            // determine new state register value for pin bit
            if (state.isHigh()) {
                states |= pinBit(pin);
            } else {
                states &= ~pinBit(pin);
            }

            // update state register value on hardware chip
            i2c.writeRegister(register, states);
        }
    }

    private PullResistance getPinPullResistance(int pin, byte register) throws IOException {

        // get current pull-up register
        byte pullup = i2c.readRegisterByte(register);

        // determine and return pin pull resistance
        return (pullup & pinBit(pin)) == pinBit(pin) ? PullResistance.PULL_UP : PullResistance.PULL_DOWN;
    }

    private void setPinPullResistance(int pin, byte register, PullResistance resistance) throws IOException {

        // atomic operation to query current pin pull-ups and then update pin pull-ups register
        synchronized (this.i2c) {

            // get current pull-up register
            byte pullup = i2c.readRegisterByte(register);

            // determine pull up value for pin bit
            if (resistance == PullResistance.PULL_UP) {
                pullup |= pinBit(pin);
            } else {
                pullup &= ~pinBit(pin);
            }

            // next update pull up resistor value
            i2c.writeRegister(register, pullup);
        }
    }
}
