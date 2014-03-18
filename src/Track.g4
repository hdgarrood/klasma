grammar Track;

track: decl+ ;

decl: channel_decl | block_decl | endline ;

channel_decl: CHANNEL SPACE WAVE channel_body ;
channel_body: block_list_line+ ;
block_list_line: endline (SPACE block_name)+ SPACE? ;
block_name: ID ;

block_decl: ID SPACE COLON SPACE BLOCK block_body ;
block_body: note_list_line+ ;
note_list_line: endline (SPACE note)+ SPACE? ;
note: NOTENAME OCTAVE? LENGTH? ;

endline : SPACE? NEWLINE ;

ID      : [a-z][a-zA-Z0-9_]* ;
COLON   : ':' ;
CHANNEL : 'CHANNEL';
BLOCK   : 'BLOCK' ;
TRACK   : 'TRACK' ;
WAVE    : 'TRIANGLE' | 'SQUARE' | 'SAWTOOTH' | 'NOISE' ;

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
        | '^' // rest
        ;
OCTAVE  : [0-9]+ ;
LENGTH  : ('+' | '-' | '.')+ ;

NEWLINE : '\r\n' | '\n' ;
SPACE   : (' ' | '\t')+ ;
COMMENT : '--' .*? NEWLINE -> skip ;
