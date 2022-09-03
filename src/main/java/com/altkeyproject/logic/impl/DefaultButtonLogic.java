package com.altkeyproject.logic.impl;

import com.altkeyproject.logic.AtomSqButtonLogic;
import com.altkeyproject.logic.ButtonState;
import com.bitwig.extension.controller.api.Application;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Transport;

public class DefaultButtonLogic implements AtomSqButtonLogic {
    private final Transport transport;
    private final Application application;

    public DefaultButtonLogic(ControllerHost host) {
        transport = host.createTransport();
        application = host.createApplication();
    }

    @Override
    public void stopButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.stop();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void playButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.play();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void recordButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.record();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void metronomeButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.isMetronomeEnabled().toggle();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void upArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            application.arrowKeyUp();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void downArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            application.arrowKeyDown();
        } else if (state == ButtonState.SHIFT_PRESSED) {

        }
    }

    @Override
    public void leftArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            application.arrowKeyLeft();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            application.selectPrevious();
        }
    }

    @Override
    public void rightArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            application.arrowKeyRight();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            application.selectNext();
        }
    }
}
