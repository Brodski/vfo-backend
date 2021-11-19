start mongodb
 - windows: B:\Program Files\MongoDB\Server\3.4\bin\mongod.exe?

GO INTO application.properties AND CHANGE THIS
  spring.profiles.active=dev
#Prod
$ sudo mvn package && sudo nohup java -jar target/restApi-0.0.1-SNAPSHOT.jar -Dprofiles.active=prod &
# without nohup
$ sudo mvn package && sudo java -jar target/restApi-0.0.1-SNAPSHOT.jar -Dprofiles.active=prod 

# Dev
$ mvn spring-boot:run 


#### mvn package    