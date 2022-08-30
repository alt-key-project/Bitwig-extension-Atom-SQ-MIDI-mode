package com.altkeyproject;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Transport;
import com.bitwig.extension.controller.ControllerExtension;

public class AtomSQExtension extends ControllerExtension
{
   private String[] deviceNames = {"ATM SQ"};
   private String[] devicePorts = {"MIDIOUT2 (ATM SQ)"};

   protected AtomSQExtension(final AtomSQExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      final ControllerHost host = getHost();


      mTransport = host.createTransport();
      host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback)msg -> onMidi0(msg));
      host.getMidiInPort(0).setSysexCallback((String data) -> onSysex0(data));
      host.defineMidiPorts(1, 1);


      if (host.platformIsWindows()) {
         host.addDeviceNameBasedDiscoveryPair(deviceNames, devicePorts);
      }
      else if (host.platformIsMac()) {
         // TODO: Set the correct names of the ports for auto detection on Mac OSX platform here
         // and uncomment this when port names are correct.
         //host.addDeviceNameBasedDiscoveryPair(["ATM SQ"], ["MIDIOUT2 (ATM SQ)"]);
      }
      else if (host.platformIsLinux()) {
         // TODO: Set the correct names of the ports for auto detection on Linux platform here
         // and uncomment this when port names are correct.
         //host.addDeviceNameBasedDiscoveryPair(["ATM SQ"], ["MIDIOUT2 (ATM SQ)"]);
      }

      host.defineController("PreSonus", "AtomSQ", "0.1", "2847337d-6d55-4028-853c-219e45da26bc", "Alt Key Project");
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
      // TODO: Implement your MIDI input handling code here.
   }

   /** Called when we receive sysex MIDI message on port 0. */
   private void onSysex0(final String data) 
   {
      // MMC Transport Controls:
      if (data.equals("f07f7f0605f7"))
            mTransport.rewind();
      else if (data.equals("f07f7f0604f7"))
            mTransport.fastForward();
      else if (data.equals("f07f7f0601f7"))
            mTransport.stop();
      else if (data.equals("f07f7f0602f7"))
            mTransport.play();
      else if (data.equals("f07f7f0606f7"))
            mTransport.record();
   }

   private Transport mTransport;
}
