package com.altkeyproject;

import com.altkeyproject.prefs.*;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Preferences;
import com.bitwig.extension.controller.api.SettableEnumValue;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class AtomSQExtPrefs {
    private final Preferences preferences;
    private final SettableEnumValue settableArrowKeyModeLR;
    private final SettableEnumValue settableArrowKeyModeUD;
    private final SettableEnumValue settableArrowKeyModeLR_SH;
    private final SettableEnumValue settableArrowKeyModeUD_SH;
    private final Consumer<AtomSQExtPrefs> onChange;
    private final ConcurrentHashMap<String, String> valueTracker = new ConcurrentHashMap<>();

    public AtomSQExtPrefs(ControllerHost host, Consumer<AtomSQExtPrefs> onChange) {
        this.onChange = onChange;
        preferences = host.getPreferences();
        settableArrowKeyModeLR = makeSettableEnumValue("Arrow buttons", "left/right", ArrowModeLR.values(), ArrowModeLR.SELECT_PREV_NEXT_ITEM);
        settableArrowKeyModeUD = makeSettableEnumValue("Arrow buttons", "up/down", ArrowModeUD.values(), ArrowModeUD.SELECT_FIRST_LAST_ITEM);
        settableArrowKeyModeLR_SH = makeSettableEnumValue("Arrow buttons", "shift+left/right", ArrowModeLRShifted.values(), ArrowModeLRShifted.ARROW_KEYS);
        settableArrowKeyModeUD_SH = makeSettableEnumValue("Arrow buttons", "shift+up/down", ArrowModeUDShifted.values(), ArrowModeUDShifted.ARROW_KEYS);
    }

    private void requestRestart() {
        onChange.accept(this);
    }

    private void printState(ControllerHost host, SettableEnumValue v) {
        host.println(v.get());
    }

    public ArrowModeLR getArrowModeLR() {
        return getFromEnum(ArrowModeLR.values(), settableArrowKeyModeLR.get());
    }

    public ArrowModeUD getArrowModeUD() {
        return getFromEnum(ArrowModeUD.values(), settableArrowKeyModeUD.get());
    }

    public ArrowModeLRShifted getArrowModeLRShifted() {
        return getFromEnum(ArrowModeLRShifted.values(), settableArrowKeyModeLR_SH.get());
    }

    public ArrowModeUDShifted getArrowModeUDShifted() {
        return getFromEnum(ArrowModeUDShifted.values(), settableArrowKeyModeUD_SH.get());
    }

    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> E getFromEnum(Enum<E>[] values, String text) {
        return (E) Arrays.stream(values).filter(e -> e.toString().equalsIgnoreCase(text)).findAny().orElse(values[0]);
    }

    private <E extends Enum<E>> SettableEnumValue makeSettableEnumValue(String section,
                                                                        String title,
                                                                        Enum<E>[] values,
                                                                        Enum<E> defaultValue) {
        SettableEnumValue enumValue = preferences.getEnumSetting(title, //
                section, Arrays.stream(values).map(Enum::toString).toArray(String[]::new),
                defaultValue.toString());
        enumValue.markInterested();
        String key = section + " | " + title;
        enumValue.addValueObserver(v -> {
            String prev = valueTracker.put(key, v);
            if (prev != null && !prev.equalsIgnoreCase(v) && Arrays.stream(values).anyMatch(e -> e.toString().equalsIgnoreCase(prev))) {
                requestRestart();
            }
        });
        return enumValue;
    }
}
