#! /bin/bash
set -e

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"
ANTLR_JAR_PATH="$BASEDIR/lib/antlr-4.2-complete.jar"

package-inf1op-jar() {
    cd "$BASEDIR"
    scripts/build.sh

    qpushd "packaging"

    echo "Copying .class files..."
    cp ../bin/* .

    echo "Extracting files from antlr jar..."
    jar xf "$ANTLR_JAR_PATH"

    echo "Creating inf1op.jar..."
    jar cmf Manifest.txt ../inf1op.jar *
    echo "Done."

    qpopd
}

qpushd() {
    pushd "$1" >/dev/null
}

qpopd() {
    popd >/dev/null
}

package-inf1op-jar
