# 基础镜像
FROM  openjdk:8-jre
# author
MAINTAINER eszhang
# 调整字符集
ENV LANG C.UTF-8
# 调整时区 +8
ENV TZ Asia/Shanghai
EXPOSE 9403
# 挂载目录
VOLUME /home/highgo
# 创建目录
RUN mkdir -p /home/highgo
# 指定路径
WORKDIR /home/highgo
# 复制jar文件到路径
COPY ./highgo-modules-message.jar /home/highgo/highgo-modules-message.jar
# 启动系统服务
ENTRYPOINT ["java","-jar","highgo-modules-medium.jar"]
