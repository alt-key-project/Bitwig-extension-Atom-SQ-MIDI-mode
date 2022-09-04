package com.altkeyproject.prefs;

public enum ArrowModeUDShifted {
    ARROW_KEYS("as arrow keys"),
    SELECT_FIRST_LAST_ITEM("select first/last item"),
    MIDI_CC("send MIDI CC");
    public final String descriptor;

    private ArrowModeUDShifted(final String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}
