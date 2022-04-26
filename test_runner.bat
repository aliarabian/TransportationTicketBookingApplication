ECHO OFF
ECHO "=== Compiling Source Code ==="
javac -d out\production\ src\main\java\com\transportation\airline\tickets\booking\boundry\*.java src\main\java\com\transportation\airline\tickets\booking\control\*.java src\main\java\com\transportation\airline\tickets\booking\entity\*.java src\main\java\com\transportation\airline\tickets\booking\boundry\dao\*.java src\main\java\com\transportation\airline\tickets\booking\boundry\dto\*.java src\main\java\sample\data\TestDataSource.java
ECHO "=== Compiling Test Code ==="
javac -d out\test\ src\test\java\com\transportation\airline\tickets\booking\boundry\PlaneTicketBookingServiceTest.java -cp lib\apiguardian-api-1.1.2.jar;lib\junit-jupiter-api-5.8.2.jar;lib\junit-jupiter-engine-5.8.2.jar;lib\junit-jupiter-params-5.8.2.jar;lib\junit-platform-engine-1.8.2.jar;lib\junit-platform-launcher-1.8.2.jar;out\production
java -jar lib\junit-platform-console-standalone-1.8.2.jar -cp out\test;out\production --scan-classpath
