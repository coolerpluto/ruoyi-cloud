# 基础镜像
FROM  openjdk:8-jre
# author
MAINTAINER eszhang

EXPOSE 8718

RUN mkdir -p /home/sentinel

# 复制jar文件到路径
COPY ./jar/sentinel-dashboard-1.8.6.jar /home/sentinel/sentinel-dashboard.jar

ENV JAVA_OPTS '-Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718'

CMD java ${JAVA_OPTS} -jar /home/sentinel/sentinel-dashboard.jar
