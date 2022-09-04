package com.altkeyproject.prefs;

public enum ArrowModeLR {
    ARROW_KEYS("as arrow keys"),
    SELECT_PREV_NEXT_ITEM("select prev/next item"),
    MIDI_CC("send MIDI CC");
    public final String descriptor;

    private ArrowModeLR(final String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}
