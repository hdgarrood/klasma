ANTLR_JAR_PATH="$PWD/lib/antlr-4.2-complete.jar"
CLASSPATH="$ANTLR_JAR_PATH:$CLASSPATH"

if find gen_src/*.java 2>/dev/null; then
    rm gen_src/*.java
fi

cd src
java -jar "$ANTLR_JAR_PATH" -o ../gen_src Track.g4
RESULT=$?
cd ..

exit $RESULT
