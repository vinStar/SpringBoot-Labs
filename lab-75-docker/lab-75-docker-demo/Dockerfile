FROM java:openjdk-8-jdk-alpine
ADD target/*.jar app.jar

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

VOLUME /tmp /logs
ENV JAVA_OPTS="-server -Xms512M -Xmx512M -Xmn128M -XX:NewSize=128M -XX:MaxNewSize=128M -XX:SurvivorRatio=20 -XX:MaxTenuringThreshold=10 -XX:GCTimeRatio=19 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:-CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=256m"


# ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 8080