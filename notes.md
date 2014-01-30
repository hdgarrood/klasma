## Objects in the API

Note that (for now, at least) any part of this could change at any time.

* a **NoteName** is something from the set { Ab, A, A#, Bb, B, C, C#, Db, D, D#, Eb, E, F, F#, Gb, G, G# }. Note that we treat consecutive sharps and flats (eg, A# and Bb) equivalently.
* a **NoteValue** represents the length of a note. Possibly represented by a double which is the ratio of that note's length to a semibreve (whole note). So a crotchet is 1/4, a quaver is 1/8, etc.
* a **Pitch** consists of a NoteName and a natural number, specifying which octave: eg, (C, 4) would represent middle C.
* a **Note** consists of a NoteValue and possibly also a Pitch (the absence of a Pitch implies that it is a rest)
* a **Waveform** is like a blueprint of the shape of a wave. It should be possible to create a wave of any frequency and amplitude by scaling a Waveform. Currently the plan is to implement this as a function mapping time (over one single period) to amplitude, that is, [0, 1] -> [-1, 1]
* a **Channel** is a Waveform and an ordered list of Notes. To play a Channel, play each of the Notes in order (a Note can only be started after the previous one has finished) using the Channel's Waveform.
* a **Track** is one or more Channels, which should be played concurrently.

## Javax Sound API research

* Looks fairly easy; just need to generate a stream of byte arrays.
* Eg: http://jsresources.org/examples/OscillatorPlayer.html
* Worth a read: http://docs.oracle.com/javase/tutorial/sound/sampled-overview.html

## WAV format

* I think the Javax Sound API class AudioOutputStream is designed to allow for
  multiple outputs; that is, playing directly, or generating WAV data, or
  generating MP3 data, or anything. That probably means that the WAV generation
  has already been done for us.
