Tomcat Version: 6
JVM Version: 1.6

===============Development=====================

1. Ensure that your <tomcat>/conf/tomcat-users.xml file has the following defined:

<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
  <role rolename="manager"/>
  <role rolename="admin"/>
  <user username="admin" password="admin" roles="admin,manager"/>
</tomcat-users>


2. Setup Maven and modify .m2\settings.xml

<servers>
    <server>
        <id>tomcat</id>
        <username>admin</username>
        <password>admin</password>
    </server>
</servers>

3. Run the tomcat from /usr/local/tomcat/bin/startup.sh

4. Go to project

mvn tomcat:deploy --to deploy the project
mvn tomcat:undeploy --to undeploy the project
mvn tomcat:redeploy --to redeploy the project

mvn clean package hibernate3:hbm2ddl -- to generate schema

--single command to do in one shot-----
mvn tomcat:undeploy clean package tomcat:deploy
mvn -P jrebel clean package tomcat:deploy

===============debug start=========
export JPDA_TRANSPORT=dt_socket
./catalina.sh jpda start
./catalina.sh jpda stop
--Debug port:8000
================JREBEL==========
add in catilina.sh
JAVA_OPTS="-javaagent:/usr/local/jrebel/jrebel.jar"
============Increase tomcat memory========
add in catilina.sh
CATALINA_OPTS="-Xms128m -Xmx128m"

====================Production====================
1. mvn package
will create war file in target folder
then copy it to tomcat and start the tomcat

by default java.io.tmpdir is temp dir inside tomcat if want to change it
CATALINA_TMPDIR in catilina.sh
moreover there should be some script to delete file preodically from that folder.

====================To Do a Release==============
1) Make sure you have the latest updates.
2) Make sure you are in the master branch
3) Issue this command: mvn -DautoVersionSubmodules=true release:prepare
5) You will have a target directory. Copy the generated war from there