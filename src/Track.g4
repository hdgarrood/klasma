grammar Track;

track: track_decl decl* ;

track_decl: TRACK track_body ;
track_body: channel_list_line+ ;
channel_list_line: ENDLINE (SPACE channel_name)+ SPACE? ;
channel_name: ID ;

decl: channel_decl | block_decl | ENDLINE ;

channel_decl: ID COLON CHANNEL WAVE channel_body ;
channel_body: block_list_line+ ;
block_list_line: ENDLINE (SPACE block_name)+ SPACE? ;
block_name: ID ;

block_decl: ID COLON BLOCK block_body ;
block_body: note_list_line+ ;
note_list_line: ENDLINE (SPACE note)+ SPACE? ;
note: NOTENAME OCTAVE? LENGTH? ;

ID      : [a-zA-Z][a-zA-Z0-9_]+ ;
COLON   : ':' ;
CHANNEL : 'channel';
BLOCK   : 'block' ;
TRACK   : 'track' ;
WAVE    : 'triangle' | 'square' | 'sawtooth' | 'noise' ;

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
        | 'R' // rest
        ;
OCTAVE  : [0-9]+ ;
LENGTH  : ('+' | '-' | '.')+ ;

NEWLINE : '\r\n' | '\n' ;
ENDLINE : SPACE? NEWLINE ;
SPACE   : (' ' | '\t')+ ;
COMMENT : '--' .*? NEWLINE -> skip ;
