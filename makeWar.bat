@echo off

DEL /S /Y out\app\
robocopy /MIR src\main\webapp\ out\app\
mkdir out\app\WEB-INF\classes
javac -d out\app\WEB-INF\classes\ --source-path src\main\java\ src\main\java\ui\servlet\frontcontroller\*.java src\main\java\ui\servlet\filter\auth\*.java src\main\java\ui\servlet\filter\*.java -cp lib\javax.servlet-api-4.0.1.jar
cd out\app\
jar -cvf app.war .
COPY app.war D:\Server\apache-tomcat-9.0.56\webapps
cd ..\..