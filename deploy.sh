#!/bin/bash

REPOSITORY=/home/ec2-user/SSAFPossible/UnICode

cd $REPOSITORY

# docker stop UnICode
# docker rm UnICode
# docker rmi UnICode

docker build -t UnICode .
docker run -d -p 8080:8080 —name UnICode UnICode
