#!/usr/bin/env bash
echo "欢迎使用脚本："
echo ""
echo -e "grop_log.sh \${tomcat_ip} \${script} \${log_dir}  "
# 全局命令片断
cmd_log_dir='`ps -ef | grep tomcat | grep -v grep | grep -P  -o "(?<=\-Dcatalina.base=)\\S+" | sed "s/$/logs\//"`'

param_num=$#
servers=$1
script=$2
log_dir=$3

for tomcat_ip in ${servers}; do
  log_dir=$(ssh ${tomcat_ip} "cd ${cmd_log_dir} && pwd")
  ssh_cmd="ssh ${tomcat_ip} \"cd ${log_dir} && ${script}\""
  echo -e "========================== begin ======================="
  echo -e "目标服务器 >:${tomcat_ip}"
  echo -e "目标文件夹 >:${log_dir}"
  echo -e "目标命令   >:${ssh_cmd}"
  ssh ${tomcat_ip} "cd ${log_dir} && ${script}"

  echo -e "========================== end =======================\n\n\n"
done

# read -p ">:" index
