#! /bin/bash
set -e

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"
ANTLR_JAR_PATH="$BASEDIR/lib/antlr-4.2-complete.jar"
CLASSPATH="$ANTLR_JAR_PATH:$CLASSPATH"
SOURCEPATH="src:gen_src"
OUTPUTDIR="bin"
GRAMMAR_FILE="Track.g4"

compile_grammar() {
    echo "Compiling grammar..."

    clean_files gen_src/*

    qpushd src >/dev/null
    java -jar "$ANTLR_JAR_PATH" -o ../gen_src "$GRAMMAR_FILE"
    RESULT=$?
    qpopd >/dev/null
    return $RESULT
}

compile_sources() {
    echo "Compiling Java source..."
    javac \
        -sourcepath "$SOURCEPATH"   \
        -cp "$CLASSPATH"            \
        -d "$OUTPUTDIR"             \
        src/*.java
}

clean() {
    echo "Cleaning..."
    clean_files bin/*
    clean_files gen_src/*
}

clean_files() {
    if find $@ >/dev/null 2>/dev/null; then
        rm $@
    fi
}

qpushd() {
    pushd "$1" >/dev/null
}

qpopd() {
    popd >/dev/null
}

qpushd "$BASEDIR"
if [[ $1 == 'clean' ]]; then
    clean
else
    compile_grammar && compile_sources
fi
qpopd
