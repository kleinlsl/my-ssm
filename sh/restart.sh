#!/bin/bash
# Author                :       zcl
# Create at             :       2021/7/30
#
################################################ 函数定义 #############################################
#全局变量
#is_start='yes'
#declare -A map;
j=1
for i in $(ls /home/tujia/www/); do
  map[$j]=$i
  echo "$j.$i"
  j=$(($j + 1))
done
#read -p "请选择:" sel

function restart_tomcat() {
  local app_Code="$1"

  case $level in
  error) echo -e "\033[41;30;1m${msg}\033[0m" ;;
  warn) echo -e "\033[43;30;1m${msg}\033[0m" ;;
  info) echo -e "\033[47;30;1m${msg}\033[0m" ;;
  concern) echo -e "\033[42;30;1m${msg}\033[0m" ;;
  *) echo "${msg}" ;;
  esac
}
app_Code=$(echo ${map[$sel]})
jacoco_path="/home/tujia/www/${app_Code}/jacoco"
websrv_root_vm="/home/tujia/www/${app_Code}/webapps/ROOT/"
healthcheck_file="${websrv_root_vm}/healthcheck.html"
#下载jacoco包放到指定的地方
if [[ ! -d "$jacoco_path" ]]; then
  sudo mkdir -p $jacoco_path
fi
sudo wget -nv -O $jacoco_path/jacocoagent.jar "http://mvn.corp.tujia.com/nexus/service/local/repositories/thirdparty/content/org/jacoco/org.jacoco.agent/0.8.4/org.jacoco.agent-0.8.4.jar"
#修改startenv.sh添加javaagent启动参数
#echo '{'; \
#echo 'echo "# jacoco agent"'; \
#echo 'echo "CATALINA_OPTS=\"\$CATALINA_OPTS -javaagent:/home/tujia/www/${app_Code}/jacoco/jacocoagent.jar=includes=*,output=tcpserver,port=9527,address=0.0.0.0,append=true -Xverify:none\""'; \
#echo 'echo "export CATALINA_OPTS"'; \
#echo '} >> /home/tujia/www/${app_Code}/startenv.sh'; \
echo -e "\n# jacoco agent" | sudo tee -a /home/tujia/www/${app_Code}/startenv.sh
echo -e "\nCATALINA_OPTS=\"\$CATALINA_OPTS -javaagent:/home/tujia/www/${app_Code}/jacoco/jacocoagent.jar=includes=*,output=tcpserver,port=9527,address=0.0.0.0,append=true -Xverify:none\"" | sudo tee -a /home/tujia/www/${app_Code}/startenv.sh
echo -e "export CATALINA_OPTS" | sudo tee -a /home/tujia/www/${app_Code}/startenv.sh
if [ $is_start == 'yesno' ]; then
  #摘除healthcheck
  if [[ -f "$healthcheck_file" ]]; then
    sudo rm -f "$healthcheck_file"
    if [ $? -eq 0 ]; then
      echo "info" "[+] $(date +'%H:%M:%S') info: rm $healthcheck_file success"
    else
      echo "error" "[-] $(date +'%H:%M:%S') error: rm $healthcheck_file failed"
      exit 1
    fi
  else
    echo "warn" "[-] $(date +'%H:%M:%S') warn: file $healthcheck_file not found"
    exit 0
  fi
  sleep 10
  #重启服务，挂上healthcheck（或者是提示他们自己重启服务）
  echo "------重启服务------"
  sudo /home/tujia/tools/bin/restart_tomcat.sh /home/tujia/www/${app_Code}

  echo "------挂回healthcheck------"
  if [ ! -f "$healthcheck_file" ]; then
    sudo touch "$healthcheck_file"
    if [ $? -eq 0 ]; then
      echo "info" "[+] $(date +'%H:%M:%S') info: touch $healthcheck_file success"
    else
      echo "error" "[-] $(date +'%H:%M:%S') error: touch $healthcheck_file failed"
      exit 1
    fi
  else
    echo "warn" "[-] $(date +'%H:%M:%S') warn: file $healthcheck_file already exist"
    exit 0
  fi
else
  echo '------请手动重启对应服务，jacoco才会生效------'
fi
