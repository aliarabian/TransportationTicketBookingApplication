rm -rf out/app/*
cp -R src/main/webapp/* out/app
mkdir out/app/WEB-INF/classes
javac -d out/app/WEB-INF/classes src/main/java/ui/servlet/frontcontroller/*.java -cp lib/javax.servlet-api-4.0.1.jar
cd out/app/
jar -cvf app.war .
cp -f app.war $TOMCAT_HOME/webapps 


