## Projeto Especialista Rest

#### Build and run

#### Configurations

Open file `application.properties` file contains informations for conections.

#### Requirements

- Java 12
- Maven > 3.0
- Spring 2.1.7.RELEASE `pom.xml`
- mySql

#### From terminal

Go on the project's root folder, then type:

    $ mvn spring-boot:run

#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.

### Anotations

- Content-type = property used to inform, what format the request will be sent.
- Accept = property used to inform, what format the request will be return.
- Status HTTP = 204 NoContent, status used when not have result, example verb delete 
	- 409 Conflict, status used when not is possibled requisition, is necessary send body with justification.
- Class service not return ResponseEntity, not know protocol HTTP
- 400 "Bad Request" status used when information of user there are incorrect
- 404 "Not Found" status used when an recurse not exist example = endpoint /restaurantes not exist



