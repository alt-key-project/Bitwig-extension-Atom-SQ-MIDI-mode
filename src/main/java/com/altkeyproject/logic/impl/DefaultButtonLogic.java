package com.altkeyproject.logic.impl;

import com.altkeyproject.AtomSQExtPrefs;
import com.altkeyproject.logic.AtomSqButtonLogic;
import com.altkeyproject.logic.ButtonState;
import com.bitwig.extension.controller.api.*;

public class DefaultButtonLogic implements AtomSqButtonLogic {
    private final Transport transport;
    private final Application application;
    private final AtomSQExtPrefs prefs;

    public DefaultButtonLogic(ControllerHost host, AtomSQExtPrefs prefs) {
        transport = host.createTransport();
        application = host.createApplication();
        this.prefs = prefs;
        registerInterest();
    }

    private void registerInterest() {
        transport.preRoll().markInterested();
        transport.isMetronomeAudibleDuringPreRoll().markInterested();
    }

    @Override
    public void stopButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.stop();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            application.undo();
        }
    }

    @Override
    public void playButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.play();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            transport.isArrangerLoopEnabled().toggle();
        }
    }

    @Override
    public void recordButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.record();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            application.getAction("Save").invoke();
        }
    }

    @Override
    public void metronomeButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            transport.isMetronomeEnabled().toggle();
        } else if (state == ButtonState.SHIFT_PRESSED) {
            boolean isActive = transport.isMetronomeAudibleDuringPreRoll().get();
            if (!isActive) {
                if (transport.preRoll().get().equalsIgnoreCase("none")) {
                    transport.preRoll().set("one_bar");
                }
                transport.isMetronomeAudibleDuringPreRoll().set(true);
            } else {
                transport.isMetronomeAudibleDuringPreRoll().set(false);
            }
        }
    }


    @Override
    public void upArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            switch (prefs.getArrowModeUD()) {
                case ARROW_KEYS:
                    application.arrowKeyUp();
                    break;
                case SELECT_FIRST_LAST_ITEM:
                    application.selectFirst();
                    break;

            }
        } else if (state == ButtonState.SHIFT_PRESSED) {
            switch (prefs.getArrowModeUDShifted()) {
                case ARROW_KEYS:
                    application.arrowKeyUp();
                    break;
                case SELECT_FIRST_LAST_ITEM:
                    application.selectFirst();
                    break;
            }
        }
    }

    @Override
    public void downArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            switch (prefs.getArrowModeUD()) {
                case ARROW_KEYS:
                    application.arrowKeyDown();
                    break;
                case SELECT_FIRST_LAST_ITEM:
                    application.selectLast();
                    break;
            }
        } else if (state == ButtonState.SHIFT_PRESSED) {
            switch (prefs.getArrowModeUDShifted()) {
                case ARROW_KEYS:
                    application.arrowKeyDown();
                    break;
                case SELECT_FIRST_LAST_ITEM:
                    application.selectLast();
                    break;
            }
        }
    }

    @Override
    public void leftArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            switch (prefs.getArrowModeLR()) {
                case ARROW_KEYS:
                    application.arrowKeyLeft();
                    break;
                case SELECT_PREV_NEXT_ITEM:
                    application.selectPrevious();
                    break;
            }
        } else if (state == ButtonState.SHIFT_PRESSED) {
            switch (prefs.getArrowModeLRShifted()) {
                case ARROW_KEYS:
                    application.arrowKeyLeft();
                    break;
                case SELECT_PREV_NEXT_ITEM:
                    application.selectPrevious();
                    break;
            }
        }
    }

    @Override
    public void rightArrowButton(ButtonState state) {
        if (state == ButtonState.PRESSED) {
            switch (prefs.getArrowModeLR()) {
                case ARROW_KEYS:
                    application.arrowKeyRight();
                    break;
                case SELECT_PREV_NEXT_ITEM:
                    application.selectNext();
                    break;
            }
        } else if (state == ButtonState.SHIFT_PRESSED) {
            switch (prefs.getArrowModeLRShifted()) {
                case ARROW_KEYS:
                    application.arrowKeyRight();
                    break;
                case SELECT_PREV_NEXT_ITEM:
                    application.selectNext();
                    break;
            }
        }
    }
}
