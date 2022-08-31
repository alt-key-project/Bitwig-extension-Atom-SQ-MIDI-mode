package com.altkeyproject;

import com.altkeyproject.logic.AtomSqLogic;
import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.ControllerExtension;

public class AtomSQExtension extends ControllerExtension
{
   ShortMidiMessage msg = null;

   protected AtomSQExtension(final AtomSQExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      final ControllerHost host = getHost();
      getHost().println("INIT AtomSQExtension");

      mTransport = host.createTransport();

      midiOut = host.getMidiOutPort(0);
      midiIn = host.getMidiInPort(0);
      midiIn.setMidiCallback((ShortMidiMessageReceivedCallback)msg -> onMidi0(msg));
      midiIn.setSysexCallback((String data) -> onSysex0(data));
      midiIn.createNoteInput("Atom SQ", "8?????","9?????","D0????","E0????");
      host.showPopupNotification("Atom SQ Initialized");
   }

   @Override
   public void exit()
   {
      // TODO: Perform any cleanup once the driver exits
      // For now just show a popup notification for verification that it is no longer running.
      getHost().showPopupNotification("Atom SQ Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }

   /** Called when we receive short MIDI message on port 0. */
   private void onMidi0(ShortMidiMessage msg) 
   {
      this.msg = msg;
      // TODO: Implement your MIDI input handling code here.
      //new AtomSqLogic(getHost()).sendMidi(msg);
      getHost().println("MIDI "+msg);
   }

   /** Called when we receive sysex MIDI message on port 0. */
   private void onSysex0(final String data) 
   {
      getHost().println("Sysex "+data);
      switch (data){
         case "f07f7f0605f7":
            getHost().println("rewind");
            mTransport.rewind();
            break;
         case "f07f7f0604f7":
            getHost().println("fastForward");
            mTransport.fastForward();
            break;
         case "f07f7f0601f7":
            getHost().println("stop");
            mTransport.stop();
            break;
         case "f07f7f0602f7":
            getHost().println("play");
            mTransport.play();
            break;
         case "f07f7f0606f7":
            getHost().println("record");
            mTransport.record();
            break;
      }
   }

   private Transport mTransport;
   private MidiOut midiOut;
   private MidiIn midiIn;
}
