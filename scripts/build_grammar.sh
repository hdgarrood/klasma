pushd src
antlr4 -o ../gen_src Track.g4
popd
pushd gen_src
javac *.java
popd
