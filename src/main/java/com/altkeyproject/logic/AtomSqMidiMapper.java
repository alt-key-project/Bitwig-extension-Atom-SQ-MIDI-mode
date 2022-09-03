package com.altkeyproject.logic;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.MidiIn;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class AtomSqMidiMapper {
    private final AtomSqButtonLogic buttonLogic;
    private final ControlChangeActions control;
    private final Consumer<String> tracer;
    private final ConcurrentLinkedQueue<Runnable> actions = new ConcurrentLinkedQueue<>();
    private ShiftableButtonHandler stopButtonHandler;
    private ShiftableButtonHandler playButtonHandler;
    private ShiftableButtonHandler recButtonHandler;
    private ShiftableButtonHandler metroButtonHandler;
    private ShiftableButtonHandler upButtonHandler;
    private ShiftableButtonHandler downButtonHandler;
    private ShiftableButtonHandler leftButtonHandler;
    private ShiftableButtonHandler rightButtonHandler;


    public AtomSqMidiMapper(AtomSqButtonLogic buttonLogic, MidiIn midiIn, Consumer<String> tracer) {
        this.buttonLogic = buttonLogic;
        this.control = new ControlChangeActions();
        this.tracer = tracer;
        midiIn.setMidiCallback((ShortMidiMessageReceivedCallback) msg -> onMidi0(msg));
        midiIn.setSysexCallback((String data) -> onSysex0(data));
        midiIn.createNoteInput("Atom SQ", "8?????", "9?????", "D?????", "E0????", "B00???", "B01???", "B040??");
        initMappings();
    }

    public void flush() {
        Runnable r = actions.poll();
        while (r != null) {
            r.run();
            r = actions.poll();
        }
    }

    private void setupShiftableButtons() {
        stopButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.stopButton(ButtonState.PRESSED),
                () -> buttonLogic.stopButton(ButtonState.RELEASED),
                () -> buttonLogic.stopButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.stopButton(ButtonState.SHIFT_RELEASED));
        playButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.playButton(ButtonState.PRESSED),
                () -> buttonLogic.playButton(ButtonState.RELEASED),
                () -> buttonLogic.playButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.playButton(ButtonState.SHIFT_RELEASED));
        recButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.recordButton(ButtonState.PRESSED),
                () -> buttonLogic.recordButton(ButtonState.RELEASED),
                () -> buttonLogic.recordButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.recordButton(ButtonState.SHIFT_RELEASED));
        metroButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.metronomeButton(ButtonState.PRESSED),
                () -> buttonLogic.metronomeButton(ButtonState.RELEASED),
                () -> buttonLogic.metronomeButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.metronomeButton(ButtonState.SHIFT_RELEASED));
        leftButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.leftArrowButton(ButtonState.PRESSED),
                () -> buttonLogic.leftArrowButton(ButtonState.RELEASED),
                () -> buttonLogic.leftArrowButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.leftArrowButton(ButtonState.SHIFT_RELEASED));
        rightButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.rightArrowButton(ButtonState.PRESSED),
                () -> buttonLogic.rightArrowButton(ButtonState.RELEASED),
                () -> buttonLogic.rightArrowButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.rightArrowButton(ButtonState.SHIFT_RELEASED));
        upButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.upArrowButton(ButtonState.PRESSED),
                () -> buttonLogic.upArrowButton(ButtonState.RELEASED),
                () -> buttonLogic.upArrowButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.upArrowButton(ButtonState.SHIFT_RELEASED));
        downButtonHandler = new ShiftableButtonHandler(
                () -> buttonLogic.downArrowButton(ButtonState.PRESSED),
                () -> buttonLogic.downArrowButton(ButtonState.RELEASED),
                () -> buttonLogic.downArrowButton(ButtonState.SHIFT_PRESSED),
                () -> buttonLogic.downArrowButton(ButtonState.SHIFT_RELEASED));
    }

    private void initMappings() {
        setupShiftableButtons();
        control.onEvent(0, (data1, data2) -> data1 == 0x55 && data2 > 0,
                () -> stopButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x55 && data2 > 0,
                () -> stopButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x55 && data2 == 0,
                () -> stopButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x56 && data2 > 0,
                () -> playButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x56 && data2 > 0,
                () -> playButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x56 && data2 == 0,
                () -> playButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x57 && data2 > 0,
                () -> recButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x57 && data2 > 0,
                () -> recButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x57 && data2 == 0,
                () -> recButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x59 && data2 > 0,
                () -> metroButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x59 && data2 > 0,
                () -> metroButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x59 && data2 == 0,
                () -> metroButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x66 && data2 > 0,
                () -> leftButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x66 && data2 > 0,
                () -> leftButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x66 && data2 == 0,
                () -> leftButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x69 && data2 > 0,
                () -> rightButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x69 && data2 > 0,
                () -> rightButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x69 && data2 == 0,
                () -> rightButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x67 && data2 > 0,
                () -> upButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x67 && data2 > 0,
                () -> upButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x67 && data2 == 0,
                () -> upButtonHandler.release());

        control.onEvent(0, (data1, data2) -> data1 == 0x68 && data2 > 0,
                () -> downButtonHandler.pressNoShift());
        control.onEvent(1, (data1, data2) -> data1 == 0x68 && data2 > 0,
                () -> downButtonHandler.pressWithShift());
        control.onEvent(Arrays.asList(0,1), (data1, data2) -> data1 == 0x68 && data2 == 0,
                () -> downButtonHandler.release());
    }

    private void onMidi0(ShortMidiMessage msg) {
        if (msg.isControlChange()) {
            Runnable r = control.process(msg);
            if (r != null) {
                tracer.accept("ACTION ON MIDI status " + asHex(msg.getStatusByte()) + " channel " + asHex(msg.getChannel()) +
                        " data " + asHex(msg.getData1()) + " " + asHex(msg.getData2()));
                actions.add(r);
                return;
            }
        }
        tracer.accept("UNHANDLED MIDI status " + asHex(msg.getStatusByte()) + " channel " + asHex(msg.getChannel()) +
                " data " + asHex(msg.getData1()) + " " + asHex(msg.getData2()));
    }

    /**
     * Called when we receive sysex MIDI message on port 0.
     */
    private void onSysex0(final String data) {
        tracer.accept("UNHANDLED Sysex " + data);
    }

    private String asHex(int h) {
        String s = Integer.toHexString(h);
        if (s.length() == 1) {
            return "0" + s;
        }
        return s;
    }


    private static class ShiftableButtonHandler{
        private boolean inShiftMode = true;
        private final Runnable onPress;
        private final Runnable onRelease;
        private final Runnable onShiftPress;
        private final Runnable onShiftRelease;

        public ShiftableButtonHandler(Runnable onPress, Runnable onRelease, Runnable onShiftPress, Runnable onShiftRelease) {
            this.onPress = onPress;
            this.onRelease = onRelease;
            this.onShiftPress = onShiftPress;
            this.onShiftRelease = onShiftRelease;
        }

        public void pressNoShift() {
            inShiftMode = false;
            onPress.run();
        }

        public void pressWithShift() {
            inShiftMode = true;
            onShiftPress.run();
        }

        public void release() {
            if (inShiftMode) {
                onShiftRelease.run();
            } else {
                onRelease.run();
            }
        }
    }
}
