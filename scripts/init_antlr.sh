init_antlr() {
    local ANTLR_JAR_PATH="/usr/local/lib/antlr-4.2-complete.jar"
    export CLASSPATH="$ANTLR_JAR_PATH:$CLASSPATH"
    alias antlr4="java -jar $ANTLR_JAR_PATH"
    alias grun="java org.antlr.v4.runtime.misc.TestRig"
}

init_antlr
unset -f init_antlr
