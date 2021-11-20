start mongodb
 - windows: B:\Program Files\MongoDB\Server\3.4\bin\mongod.exe?

GO INTO application.properties AND CHANGE THIS
  spring.profiles.active=dev
#Prod
# nohup command >/dev/null 2>&1 &
$ sudo mvn package && sudo nohup java -jar target/restApi-0.0.1-SNAPSHOT.jar -Dprofiles.active=prod &
$ sudo mvn package && nohup nohup java -jar target/restApi-0.0.1-SNAPSHOT.jar -Dprofiles.active=prod  > /dev/null 2>&1 & 
# without nohup
$ sudo mvn package && sudo java -jar target/restApi-0.0.1-SNAPSHOT.jar -Dprofiles.active=prod 

# Dev
$ mvn spring-boot:run 


#### mvn package    