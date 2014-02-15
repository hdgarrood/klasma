grammar Track;

track: track_decl decl* ;

track_decl: 'track' '{' IDENT+ '}' ;

decl: block_decl | channel_decl ;

block_decl: IDENT '::' 'block' '{' block_body '}' ;
block_body: (note)+ ;
note: NOTENAME OCTAVE? LENGTH? ;

channel_decl: IDENT '::' 'channel' WAVE '{' channel_body '}' ;
channel_body: (block_name)+ ;
block_name: IDENT ;

NOTENAME: 'Ab'
        | 'A'
        | 'A#'
        | 'Bb'
        | 'B'
        | 'C'
        | 'C#'
        | 'Db'
        | 'D'
        | 'D#'
        | 'Eb'
        | 'E'
        | 'F'
        | 'F#'
        | 'Gb'
        | 'G'
        | 'G#'
        | 'R'
        ;

OCTAVE: [0-9] | '10' ;
LENGTH: [-+.]+ ;

WAVE: 'triangle' | 'square' | 'sawtooth' | 'noise' ;

IDENT: [a-zA-Z][a-zA-Z0-9_]+ ;
WS: [\t\r\n ]+ -> skip ;
