package com.altkeyproject;

import com.altkeyproject.logic.AtomSqButtonLogic;
import com.altkeyproject.logic.AtomSqMidiMapper;
import com.altkeyproject.logic.impl.DebugButtonLogicWrapper;
import com.altkeyproject.logic.impl.DefaultButtonLogic;
import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.ControllerExtension;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;


public class AtomSQExtension extends ControllerExtension {
    private static final boolean DEBUGMODE = false;
    private MidiIn midiIn;
    private AtomSqButtonLogic logic;
    private final AtomicReference<AtomSqMidiMapper> midiMapper = new AtomicReference<>();
    private final AtomicBoolean notifiedPlatform = new AtomicBoolean(false);
    private Consumer<String> trace = (s) -> {};
    private AtomSQExtPrefs prefs;
    private ControllerHost host;

    protected AtomSQExtension(final AtomSQExtensionDefinition definition, final ControllerHost host) {
        super(definition, host);
    }

    @Override
    public void init() {
        host = getHost();
        midiIn = host.getMidiInPort(0);
        prefs = new AtomSQExtPrefs(host, this::requestReInit);

        if ((host.platformIsLinux() || host.platformIsMac()) && !notifiedPlatform.getAndSet(true)) {
            host.showPopupNotification("Auto-detection of MIDI port not yet implemented under Linux and Mac for the Atom SQ extension. Please send me the input/output MIDI port names!");
        }

        if (DEBUGMODE) {
            trace = host::println;
            trace.accept("--------------------------");
        }
        if (DEBUGMODE) {
            logic = new DebugButtonLogicWrapper(new DefaultButtonLogic(host, prefs), host::println);
        } else {
            logic = new DefaultButtonLogic(host, prefs);
        }
        initNewMidiMapper(prefs);
    }

    private void requestReInit(AtomSQExtPrefs prefs) {
        host.restart();
        host.requestFlush();
    }

    private void initNewMidiMapper(AtomSQExtPrefs prefs) {
        midiMapper.set(new AtomSqMidiMapper(logic, midiIn, prefs, trace));
    }

    @Override
    public void exit() {
        logic = null;
        midiMapper.set(null);
        midiIn = null;
    }

    @Override
    public void flush() {
        midiMapper.get().flush();
    }
}
