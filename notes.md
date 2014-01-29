## Objects in the API

* a **NoteName** (can anyone think of a better name?) is something from the set { A, A#, B, C, C#, D, D#, E, F, F#, G, G# }
* a **Pitch** consists of a NoteName and a natural number, specifying which octave: eg, (C, 4) would represent middle C.
* a **Note** contains a length (milliseconds?) and possibly also a Pitch (the absence of a Pitch implies that it is a rest)
* a **Waveform** is like a blueprint of the shape of a wave. It should be possible to create a wave of any frequency using a Waveform. Currently the plan is to implement this as a function mapping time (over one single period) to amplitude, that is, [0, 1] -> [-1, 1]
* a **Channel** is a Waveform and an ordered list of Notes. To play a Channel, play each of the Notes in order (a Note can only be started after the previous one has finished) using the Channel's Waveform.
* a **Track** is one or more Channels, which should be played concurrently.

## Javax Sound API research

* Looks fairly easy; just need to generate a stream of byte arrays.
* Eg: http://jsresources.org/examples/OscillatorPlayer.html
