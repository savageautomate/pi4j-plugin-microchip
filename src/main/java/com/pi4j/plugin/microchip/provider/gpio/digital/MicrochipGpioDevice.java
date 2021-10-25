package com.pi4j.plugin.microchip.provider.gpio.digital;

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.Digital;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;

public interface MicrochipGpioDevice  {
    void initialize(Digital io);
    DigitalState state(Digital io);
    void state(Digital io, DigitalState state) throws IOException;
    void shutdown(Digital io, Context context) throws ShutdownException;
    void pull(Digital io, PullResistance resistance);
}
