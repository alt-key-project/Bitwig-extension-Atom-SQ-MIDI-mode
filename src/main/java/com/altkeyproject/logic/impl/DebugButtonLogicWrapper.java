package com.altkeyproject.logic.impl;

import com.altkeyproject.logic.AtomSqButtonLogic;
import com.altkeyproject.logic.ButtonState;
import com.bitwig.extension.controller.api.Application;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Transport;

import java.util.function.Consumer;

public class DebugButtonLogicWrapper implements AtomSqButtonLogic {
    private final AtomSqButtonLogic wrapped;
    private final Consumer<String> tracer;

    public DebugButtonLogicWrapper(AtomSqButtonLogic wrapped, Consumer<String> tracer) {
        this.wrapped = wrapped;
        this.tracer = tracer;
    }

    private void trace(String button, ButtonState state) {
        tracer.accept("BUTTON '"+button+"': "+state.name());
    }

    @Override
    public void stopButton(ButtonState state) {
        trace("STOP", state);
        wrapped.stopButton(state);
    }

    @Override
    public void playButton(ButtonState state) {
        trace("PLAY", state);
        wrapped.playButton(state);
    }

    @Override
    public void recordButton(ButtonState state) {
        trace("RECORD", state);
        wrapped.recordButton(state);
    }

    @Override
    public void metronomeButton(ButtonState state) {
        trace("METRONOME", state);
        wrapped.metronomeButton(state);
    }

    @Override
    public void upArrowButton(ButtonState state) {
        trace("UP_ARROW", state);
        wrapped.upArrowButton(state);
    }

    @Override
    public void downArrowButton(ButtonState state) {
        trace("DOWN_ARROW", state);
        wrapped.downArrowButton(state);
    }

    @Override
    public void leftArrowButton(ButtonState state) {
        trace("LEFT_ARROW", state);
        wrapped.leftArrowButton(state);
    }

    @Override
    public void rightArrowButton(ButtonState state) {
        trace("RIGHT_ARROW", state);
        wrapped.rightArrowButton(state);
    }
}
