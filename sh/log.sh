#!/usr/bin/env bash
echo "欢迎使用脚本："
echo ""
# 全局命令片断
cmd_cd_log_dir='cd `ps -ef | grep tomcat | grep -v grep | grep -P  -o "(?<=\-Dcatalina.base=)\\S+" | sed "s/$/logs\//"`'
cmd_export_grep_options="export GREP_OPTIONS='--color=always'"
servers='l-tnshdsctriptask3.rd.tj.cna'
LINE_LIMIT=""

function func_log_print() {
  local level="$1"
  local msg="$2"

  case $level in
  error) echo -e "\033[41;30;1m${msg}\033[0m" ;;
  warn) echo -e "\033[43;30;1m${msg}\033[0m" ;;
  info) echo -e "\033[47;30;1m${msg}\033[0m" ;;
  concern) echo -e "\033[42;30;1m${msg}\033[0m" ;;
  *) echo "${msg}" ;;
  esac
}

func_log_print error "123"

function read_line_limit() {
  read -p "查询行数 >: " lines
  LINE_LIMIT=" | head -n $lines"
  if [[ -z "$lines" ]]; then
    echo "行数无限制！"
    LINE_LIMIT=""
  fi
}

function read_find_keyword() {
  read -p "查询关键词 >: " keyword
}

#read_line_limit
#read_find_keyword
keyword='123'
LINE_LIMIT=10
echo "查询关键词：${keyword}"
echo "查询行数：${LINE_LIMIT}"

for tomcat_ip in ${servers}; do
  echo -e "目标服务器:${tomcat_ip},关键字:${keyword}"
  echo "ssh" "${tomcat_ip}" "'${cmd_cd_log_dir}" && grep --color=always "${keyword}" catalina.out "${LINE_LIMIT}'"
  ssh "${tomcat_ip}" "'${cmd_cd_log_dir}'" && grep --color=always "${keyword}" catalina.out "${LINE_LIMIT}"
done

read -p ">:" index
