FROM openjdk:1.8

LABEL maintainer = "huangxichu@qq.com"

VOLUME /tmp

EXPOSE 7009

ARG JAR_FILE=server_demo-1.0.0.jar

ARG JAVA_OPTS="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"

ADD ${JAR_FILE} server_demo.jar

ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar /server_demo.jar"]
#ENTRYPOINT ["sh","-c","java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Djava.security.egd=file:/dev/./urandom -jar /server_demo_7009.jar"]
