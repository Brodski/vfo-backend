# restApi
N-tier web app with Presentation, Logic, and Data layer.
Java backend using Spring Boot framework.
React front end.


#builds in /target
mvn spring-boot:run
mvn package
java -jar target/restApi-0.0.1-SNAPSHOT.jar
mvn .\myfristspringboot\:run

###### Go to last commit
Undo last commit but keep local changes:
git reset --soft HEAD~1

Go to last commit:
git reset --hard HEAD


Deployment notes:
- git clone https://github.com/Brodski/restApi.git
- install mongodb
- install maven (if you havnt already)

sudo apt install default-jdk
wget https://download.oracle.com/otn-pub/java/jdk/13.0.1+9/cec27d702aa74d5a8630c65ae61e4305/jdk-13.0.1_linux-x64_bin.rpm