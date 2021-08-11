#!/bin/sh
export TOMCAT_USER="tomcat"
export JAVA_OPTS="-Xms4196m -Xmx4196m -XX:NewSize=512m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -server -XX:+DisableExplicitGC -Dtujia.logs=$CATALINA_BASE/logs -Dtujia.cache=$CATALINA_BASE/cache -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=1 -Xloggc:$CATALINA_BASE/logs/gc.log"
#export JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=127.0.0.1:9005"
export JAVA_OPTS="-Ddba.zk=dbazk.beta.qunar.com:2181"
chown -R tomcat:tomcat $CATALINA_BASE/logs
chown -R tomcat:tomcat $CATALINA_BASE/cache
chown -R tomcat:tomcat $CATALINA_BASE/conf
chown -R tomcat:tomcat $CATALINA_BASE/work
chown -R tomcat:tomcat $CATALINA_BASE/temp

sudo yum -y install mlocate && sudo updatedb