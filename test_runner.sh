#!/bin/bash

# Compiling Source Code
javac -d out/production/ --source-path src/main/java/ src/main/java/com/platform/TransportationTicketBookingApplication.java
# Compiling Test Code
javac -d out/test/  src/test/java/com/transportation/airline/tickets/booking/boundry/PlaneTicketBookingServiceTest.java -cp lib/junit-jupiter-api-5.8.2.jar:lib/junit-jupiter-params-5.8.2.jar:lib/junit-jupiter-engine-5.8.2.jar:out/production/:lib/apiguardian-api-1.1.2.jar
# Runing Tests 
java -jar lib/junit-platform-console-standalone-1.8.2.jar -cp out/production/:out/test --scan-classpath

