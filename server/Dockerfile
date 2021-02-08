FROM registry.cn-shanghai.aliyuncs.com/dbshs-pub/openjdk:8-jdk-alpine-font
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
VOLUME /tmp
ARG JAR_FILE=target/admin-0.0.1-SNAPSHOT.jar
ARG active=dev
ENV active=${active}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${active}"]
