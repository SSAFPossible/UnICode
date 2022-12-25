#!/bin/bash

REPOSITORY=/home/ec2-user/ssafpossible/unicode

cd $REPOSITORY

# docker stop UnICode
# docker rm UnICode
# docker rmi UnICode

docker build -t unicode .
docker run -d 8080:8080 —name unicode unicode
