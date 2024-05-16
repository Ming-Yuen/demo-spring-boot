# 基于官方的 OpenJDK 11 镜像作为基础镜像
FROM openjdk:8

# 设置工作目录
WORKDIR /app

# 将构建环境中的 JAR 文件复制到容器中的 /app 目录
COPY demo/demo-report/target/demo-report-1.0.1-SNAPSHOT.jar /app/demo.jar

# 暴露容器监听的端口
EXPOSE 8080

# 定义容器启动时执行的命令
CMD ["java", "-jar", "/app/demo.jar"]