FROM maven:3.3-jdk-8
ADD ./start-dockerfile.sh app.sh
ENTRYPOINT ["/bin/sh","app.sh"]
