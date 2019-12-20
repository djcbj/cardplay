# cardplay目标文件部署方式
## 1. 部署步骤（Windows本地）：
  1. 将target\jswtc.war文件拷贝到本地tomcat目录apache-tomcat-9.0.26\webapps\ROOT下
  2. 双击apache-tomcat-9.0.26\bin\startup.bat。
## 2. 部署步骤（Linux服务器）：
  1. 将target\jswtc.war文件重命名为ROOT.war，拷贝到服务器tomcat目录/var/lib/tomcat9/webapps下
  2. cd /var/bin进去启动文件目录
  3. ./startup.sh启动tomcat服务器
