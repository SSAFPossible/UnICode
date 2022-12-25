#!/bin/bash
# clone git
git clone https://github.com/SSAFPossible/UnICode.git

# move
cd UnICode

# build
mvn package

# run
cd target
java -jar UniCode-0.0.1-SNAPSHOT.jar
