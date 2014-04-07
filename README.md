# klasma

Klasma is a chiptune music composition toolkit. It reads text files written in
a purpose-designed DSL, and produces audio data, which can then be played or
written to a WAV file.

## prerequisites

Just Java (>= 1.6?) and make. Simply clone the repository and `make`; this
should produce a klasma.jar, which contains everything.

## use

For now, klasma is a command-line program only.

```
$ java -jar klasma.jar --help
usage: klasma [-i INPUT-FILE] (-o OUTPUT-FILE|--stdout|--play)
options:
  -i INPUT-FILE:    Specify a file to use as input.
                    Default: read from standard input.

  -o OUTPUT-FILE:   Specify a the name of a WAV file to write.

  --stdout:         Write WAV data to standard output.

  --play:           Play the track.
```

## compose

### notes

The most basic object in klasma is a note. Notes are written in a syntax based
on scientific pitch notation; a note name, followed by an optional octave,
followed by an optional length. For example:

```
C     -- A note omitting the octave and length.
C4    -- A note including the octave, but not the length.
D#3++ -- A note including both an octave and a length.
```

The note name can be anything from the list `C`, `C#`, `Db`, `D`, `D#`, and so
on, or `^` to signify a rest.

The octave is an integer, representing the octave number. Klasma uses standard
concert pitch, so `A4` is 440Hz.

The length is a (possibly zero-length) series of `+` or `-`, followed
optionally by a `.`. Each `+` doubles the length of the note, and each `-`
halves it. A `.` multiplies the length by 1.5, just like a dotted note in sheet
music. The default length is a crotchet (quarter-note). For example:

* `C` is a crotchet.
* `C+` is a minim.
* `C++` is a semibreve.
* `C--` is a semiquaver.
* `C--.` is a dotted semiquaver.

### blocks

A block is simply a name attached to a list of notes. Blocks are defined like
so:

```
<blockName> : BLOCK
    <note> [<note>...]
```

For example:

```
cMajorScale : BLOCK
    C+
    D E F G A B
    C5+
    B A G F E D
    C+

rest : BLOCK
    ^++
```

Klasma is sensitive to whitespace; every line of notes must be indented so that
it's clear which block they belong to.

### waveforms

Klasma provides you with four waveforms: `SQUARE`, `TRIANGLE`, `SAWTOOTH`,
`NOISE`. These control how a note is turned into audio data, and they affect
the timbre of the note.

### channels

A channel consists of a channel name, a waveform name, and a list of blocks.

```
<channelName> : CHANNEL <waveform>
    <block> [<block> ...]
```

For example:

```
cMajorScaleThreeTimes : CHANNEL SQUARE
    cMajorScale rest cMajorScale rest cMajorScale
```

This will play a C major scale three times, with rests in between, with a
square wave.

### tracks

A track is what you get from processing a whole file, which will consist of
a number of block and channel declarations. When you ask klasma to compile a
track, it will produce audio data by starting all the declared channels at the
same time, and playing them concurrently.

There are some example tracks in the examples/ directory; see
[examples/example1.txt](examples/example1.txt) to get you started. Enjoy!
