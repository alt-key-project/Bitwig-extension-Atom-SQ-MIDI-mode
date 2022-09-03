package com.altkeyproject;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class AtomSQExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("064b2476-088d-4fa0-a4c2-ba1092d869cd");
   
   public AtomSQExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Atom SQ - Midi Mode";
   }
   
   @Override
   public String getAuthor()
   {
      return "Alt Key Project";
   }

   @Override
   public String getVersion()
   {
      return "1.0.0";
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
      return "Atom SQ";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 17;
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
         list.add(new String[]{"ATM SQ"}, new String[]{"MIDIOUT2 (ATM SQ)"});
      }
      else if (platformType == PlatformType.MAC)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
      }
      else if (platformType == PlatformType.LINUX)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0"}, new String[]{"Output Port 0"});
      }
   }

   @Override
   public AtomSQExtension createInstance(final ControllerHost host)
   {
      return new AtomSQExtension(this, host);
   }
}
