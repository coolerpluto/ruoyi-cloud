# 基础镜像
FROM  openjdk:8-jre
# author
MAINTAINER eszhang
EXPOSE 9401
# 挂载目录
VOLUME /home/highgo
# 创建目录
RUN mkdir -p /home/highgo
# 指定路径
WORKDIR /home/highgo
# 复制jar文件到路径
COPY ./highgo-crm.jar /home/highgo/highgo-crm.jar
# 启动系统服务
ENTRYPOINT ["java","-jar","highgo-crm.jar"]
