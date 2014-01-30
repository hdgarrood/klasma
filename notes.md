## Objects in the API

Note that (for now, at least) any part of this could change at any time.

* a **NoteName** is something from the set { Ab, A, A#, Bb, B, C, C#, Db, D, D#, Eb, E, F, F#, Gb, G, G# }. Note that we treat consecutive sharps and flats (eg, A# and Bb) equivalently.
* a **NoteValue** represents the length of a note. Possibly represented by a double which is the ratio of that note's length to a semibreve (whole note). So a crotchet is 1/4, a quaver is 1/8, etc.
* a **Pitch** consists of a NoteName and a natural number, specifying which octave: eg, (C, 4) would represent middle C.
* a **Note** consists of a NoteValue and possibly also a Pitch (the absence of a Pitch implies that it is a rest)
* a **Waveform** is like a blueprint of the shape of a wave. It should be possible to create a wave of any frequency and amplitude by scaling a Waveform. Currently the plan is to implement this as a function mapping time (over one single period) to amplitude, that is, [0, 1] -> [-1, 1]
* a **Channel** is a Waveform and an ordered list of Notes. To play a Channel, play each of the Notes in order (a Note can only be started after the previous one has finished) using the Channel's Waveform.
* a **Track** is one or more Channels, which should be played concurrently, together with a tempo (which is specified in  crotchets/minute).

## Javax Sound API research

* Looks fairly easy; just need to generate a stream of byte arrays.
* Eg: http://jsresources.org/examples/OscillatorPlayer.html
* Worth a read: http://docs.oracle.com/javase/tutorial/sound/sampled-overview.html

## WAV format

* I think the Javax Sound API class AudioOutputStream is designed to allow for
  multiple outputs; that is, playing directly, or generating WAV data, or
  generating MP3 data, or anything. That probably means that the WAV generation
  has already been done for us.

## Individual object APIs

### NoteName

Recommended reading: [wikipedia: Scientific pitch notation]

Note: The class NoteName should contain one instance for each NoteName in the set above, and it should be possible to refer to them with, eg, `NoteName.C`. I expect sharp symbols aren't allowed, so a sharp should become, eg, `NoteName.CSharp`, and therefore, flats should be, eg, `NoteName.BFlat`

#### static NoteName parse(String str)
Try to parse a NoteName from a String, returning null if the parse failed. Examples:

    NoteName.parse("C")   // => NoteName.C
    NoteName.parse("A#")  // => NoteName.ASharp
    NoteName.parse("lol") // => null

#### int toInt()
Converts the NoteName to an Integer, giving its position in the scale. Using [wikipedia: Scientific pitch notation] as  the basis, we use 0 for C, 1 for C#, etc. up to 11 for B. This should always return an integer in the range [0, 11]. Examples:

    NoteName.C.toInt()      // => 0
    NoteName.CSharp.toInt() // => 1
    NoteName.DFlat.toInt()  // => 1
    NoteName.D.toInt()      // => 2
    NoteName.B.toInt()      // => 11

[wikipedia: Scientific pitch notation]: https://en.wikipedia.org/wiki/Scientific_pitch_notation

### Channel

#### (constructor) Channel(Waveform waveform, Note[] notes)

Creates a Channel with the given Waveform and Note array.

#### byte amplitudeAt(double sampleRate, int n)

If the sample rate is given by `sampleRate`, get the amplitude of the nth sample (0-based).
