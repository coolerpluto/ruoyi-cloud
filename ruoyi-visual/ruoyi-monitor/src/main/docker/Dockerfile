# 基础镜像
FROM  openjdk:8-jre
# author
MAINTAINER ruoyi
EXPOSE 9100
# 挂载目录
VOLUME /home/ruoyi
# 创建目录
RUN mkdir -p /home/ruoyi
# 指定路径
WORKDIR /home/ruoyi
# 复制jar文件到路径
COPY ./ruoyi-visual-monitor.jar /home/ruoyi/ruoyi-visual-monitor.jar
# 启动系统服务
ENTRYPOINT ["java","-Duser.timezone=GMT+8","-jar","ruoyi-visual-monitor.jar"]
