@echo off
rem adb-database-tool
color BF
adb -s emulator-5554 shell
rem 切换超级管理员权限su才能进入对应目录
cd /data/data/cn.netbuffer.persistencetest/databases/
sqlite3 persistence_test
rem 查询数据库信息
.dbinfo
rem 列出数据表
.table
rem 查询schema表结构信息
.schema