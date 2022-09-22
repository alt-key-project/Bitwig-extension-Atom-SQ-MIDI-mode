package com.altkeyproject;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class AtomSQExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("064b2476-188d-4fa0-24c2-bf1192d869cd");
   
   public AtomSQExtensionDefinition()
   {
   }

   @Override
   public String getErrorReportingEMail() {
      return "alt.key.project@gmail.com";
   }

   @Override
   public String getName()
   {
      return "Atom SQ (MIDI mode)";
   }
   
   @Override
   public String getAuthor()
   {
      return "Alt Key Project";
   }

   @Override
   public String getVersion()
   {
      return "1.0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "PreSonus";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Atom SQ (MIDI mode)";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 10;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 1;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 1;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
      if (platformType == PlatformType.WINDOWS)
      {
         list.add(new String[]{"ATM SQ"}, new String[]{"ATM SQ"});
         list.add(new String[]{"MIDIIN2 (ATM SQ)"}, new String[]{"MIDIOUT2 (ATM SQ)"});

      }
      else if (platformType == PlatformType.MAC)
      {
         // probably wrong
         list.add(new String[]{"ATM SQ"}, new String[]{"ATM SQ"});
         list.add(new String[]{"MIDIIN2 (ATM SQ)"}, new String[]{"MIDIOUT2 (ATM SQ)"});
      }
      else if (platformType == PlatformType.LINUX)
      {
         // probably wrong
         list.add(new String[]{"ATM SQ"}, new String[]{"ATM SQ"});
         list.add(new String[]{"MIDIIN2 (ATM SQ)"}, new String[]{"MIDIOUT2 (ATM SQ)"});
      }
   }

   @Override
   public AtomSQExtension createInstance(final ControllerHost host)
   {
      return new AtomSQExtension(this, host);
   }
}
