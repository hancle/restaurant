#!bin/bash

sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

sudo apt-get install mysql-server
mysql -u root -proot < ~/import.sql

cd /opt/
sudo wget http://apache.mirrors.pair.com/tomcat/tomcat-8/v8.0.37/bin/apache-tomcat-8.0.37.tar.gz 
sudo tar xzf apache-tomcat-8.0.37.tar.gz
sudo ln -s apache-tomcat-8.0.37 tomcat
echo "export CATALINA_HOME=\"/opt/tomcat\"" >> ~/.bashrc
source ~/.bashrc
cd /opt/tomcat
sudo ./bin/startup.sh

sudo cp ~/DinnerSeeker.war /opt/tomcat/webapps/





