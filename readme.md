# Bitwig controller extension for Atom SQ in MIDI mode

_by Alt Key Project_

This project provides basic support for using the Atom SQ as a MIDI controller in Bitwig. The aim is not to provide the 
same user interface or experience as when running Atom SQ with the natively supported DAWs, but rather to make the Atom SQ
useful in Bitwig.

## How to install

Download the latest version here:

https://www.idrive.com/idrive/sh/sh?k=d1g1i0q9d3

The archive contains a bwextension file. Put that file in your Bitwig extensions directory. For more information, read here: 

https://www.bitwig.com/support/technical_support/how-do-i-add-a-controller-extension-or-script-17/.

### How to configure

* Configure your Atom SQ to run in MIDI mode. The procedure is described in the section "Standard Controller Mode" in the Atom SQ manual.
* Check your settings for "Controllers" in Bitwig. If auto-detection has worked the Atom SQ should exist as a configured controller device named "PreSonus Atom SQ (MIDI mode)". Auto-detection may currently fail on Linux and Mac (please send me the correct port names!). If so, you may still add the controller manually.
* The Atom SQ controller exposes some configuration options for the arrow buttons, either with specific behaviour or as MIDI CC buttons.

## Feel like you want to buy me a beer?

No beer required (I brew my own), and the extension is free. But do consider subscribing to my channel on YouTube (https://www.youtube.com/channel/UC4cKvJ4hia7zULxeCc-7OcQ) if you feel like making me happy.

## More info

I have a video on youtube where I show the Atom SQ running with the extension. https://www.youtube.com/watch?v=0tkbS1fb4LE

## Contact

You can reach me (the developer and first user of the extension) at alt.key.project@gmail.com.

## License 

Copyright (c) 2022 Morgan Johansson & Alt Key Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
