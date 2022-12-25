#!/bin/bash

REPOSITORY=/home/ec2-user/ssafpossible/unicode

cd $REPOSITORY

docker stop unicode
docker rm unicode
docker rmi unicode

docker build -t unicode .
docker run -d -p 8080:8080 --name unicode unicode
