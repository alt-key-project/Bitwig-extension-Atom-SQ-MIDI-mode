package com.altkeyproject.logic;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;

import java.util.*;
import java.util.function.BiPredicate;

public class ControlChangeActions {
    private HashMap<Integer, ArrayList<RegisteredAction>> actionsMap = new HashMap<>();


    public void onEvent(Integer channel, BiPredicate<Integer, Integer> dataMatcher, Runnable action) {
        onEvent(Collections.singletonList(channel), dataMatcher, action);
    }

    public void onEvent(Collection<Integer> channels, BiPredicate<Integer, Integer> dataMatcher, Runnable action) {
        channels.forEach( n -> {
                ArrayList<RegisteredAction> actions = actionsMap.getOrDefault(n, new ArrayList<>());
                actions.add(new RegisteredAction(dataMatcher, action));
                actionsMap.put(n, actions);
            }
        );
    }

    public Runnable process(ShortMidiMessage msg) {
        if (!msg.isControlChange()) {
            return null;
        }
        List<RegisteredAction> actions = actionsMap.getOrDefault(msg.getChannel(), null);
        if (actions == null) {
            return null;
        }
        Optional<RegisteredAction> opt = actions.stream().
                filter((action) -> action.dataMatcher.test(msg.getData1(),msg.getData2())).findFirst();
        return opt.map(registeredAction -> registeredAction.action).orElse(null);
    }

    private class RegisteredAction {
        final BiPredicate<Integer, Integer> dataMatcher;
        final Runnable action;

        public RegisteredAction(BiPredicate<Integer, Integer> dataMatcher, Runnable action) {
            this.action = action;
            this.dataMatcher = dataMatcher;
        }
    }
}
