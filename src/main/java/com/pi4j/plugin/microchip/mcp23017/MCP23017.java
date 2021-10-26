package com.pi4j.plugin.microchip.mcp23017;

// MARKER INTERFACE
public interface MCP23017 {

    static final String DATASHEET = "https://ww1.microchip.com/downloads/en/devicedoc/20001952c.pdf";

    // supported I2C addresses based on hardware configured pins [A0], [A1] and [A2].
    static final int ADDRESS_000 = 0x20;
    static final int ADDRESS_001 = 0x21;
    static final int ADDRESS_010 = 0x22;
    static final int ADDRESS_011 = 0x23;
    static final int ADDRESS_100 = 0x24;
    static final int ADDRESS_101 = 0x25;
    static final int ADDRESS_110 = 0x26;
    static final int ADDRESS_111 = 0x27;

    // default settings
    static final int DEFAULT_ADDRESS = ADDRESS_000;
    static final int DEFAULT_POLLING_TIME = 50;

    // GPIO pins
    static final int GPA0 = 0;
    static final int GPA1 = 1;
    static final int GPA2 = 2;
    static final int GPA3 = 3;
    static final int GPA4 = 4;
    static final int GPA5 = 5;
    static final int GPA6 = 6;
    static final int GPA7 = 7;

    static final int GPB0 = 8;
    static final int GPB1 = 9;
    static final int GPB2 = 10;
    static final int GPB3 = 11;
    static final int GPB4 = 12;
    static final int GPB5 = 13;
    static final int GPB6 = 14;
    static final int GPB7 = 15;

    // communication registers
    static final byte REGISTER_IODIR_A = 0x00;
    static final byte REGISTER_IODIR_B = 0x01;
    static final byte REGISTER_GPINTEN_A = 0x04;
    static final byte REGISTER_GPINTEN_B = 0x05;
    static final byte REGISTER_DEFVAL_A = 0x06;
    static final byte REGISTER_DEFVAL_B = 0x07;
    static final byte REGISTER_INTCON_A = 0x08;
    static final byte REGISTER_INTCON_B = 0x09;
    static final byte REGISTER_GPPU_A = 0x0C;
    static final byte REGISTER_GPPU_B = 0x0D;
    static final byte REGISTER_INTF_A = 0x0E;
    static final byte REGISTER_INTF_B = 0x0F;
    static final byte REGISTER_INTCAP_A = 0x10;
    static final byte REGISTER_INTCAP_B = 0x11;
    static final byte REGISTER_GPIO_A = 0x12;
    static final byte REGISTER_GPIO_B = 0x13;

    static final int GPIO_A_OFFSET   = 0;
    static final int GPIO_A_PINCOUNT = 8;
    static final int GPIO_B_OFFSET   = 8;
    static final int GPIO_B_PINCOUNT = 8;
}
