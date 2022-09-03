package com.altkeyproject;

import com.altkeyproject.logic.AtomSqButtonLogic;
import com.altkeyproject.logic.AtomSqMidiMapper;
import com.altkeyproject.logic.impl.DebugButtonLogicWrapper;
import com.altkeyproject.logic.impl.DefaultButtonLogic;
import com.bitwig.extension.controller.api.*;
import com.bitwig.extension.controller.ControllerExtension;

public class AtomSQExtension extends ControllerExtension
{
   private final boolean DEBUGMODE = true;
   private MidiIn midiIn;
   private AtomSqButtonLogic logic;
   private AtomSqMidiMapper midiMapper;

   protected AtomSQExtension(final AtomSQExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      final ControllerHost host = getHost();
      midiIn = host.getMidiInPort(0);
      if (DEBUGMODE) {
         logic = new DebugButtonLogicWrapper(new DefaultButtonLogic(host), host::println);
         midiMapper = new AtomSqMidiMapper(logic, midiIn, host::println);
      } else {
         logic = new DefaultButtonLogic(host);
         midiMapper = new AtomSqMidiMapper(logic, midiIn, s -> {});
      }
   }

   @Override
   public void exit()
   {
      logic = null;
      midiMapper = null;
      midiIn = null;
   }

   @Override
   public void flush()
   {
      midiMapper.flush();
   }
}
