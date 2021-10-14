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
- Commando for flyway: mvn flyway:repair -Dflyway.configFiles=src/main/resources/flyway.properties
- O método PUT é idempotente. Um método é considerado idempotente se o resultado de uma requisição realizada com sucesso é independente do número de vezes que é executada.
- O método POST não é idempotente, pois a cada requisição haverá uma alteração no sistema. (um registro será incluído)
- Tomcat não aceita colchetes [] 
- *ERRO* Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986*
- para correção na URLS encoda os caracteres especiais -> nesse caso [ -> %5B   ] -> %5D
- para aceitar tem que costumizar o tomcat embedado que o spring boot tem (TomcatCustomizer)
- Para upload de arquivo, sempre observar que a regra principal de tamanho de arquivo é a que é configurado do application.properties, portanto
para validar individualmente cada cenario de upload de arquivo, a regra dos DTO para ser validada, ela tem que passar pela regra do application 
exemplo configurado no application 20MB e em um determinado DTO configurado 2MB a validação vai passar pelo application e vai ser barrada pela a 
validação feita no DTO.
- Para subir um servidor web, podemos usar como opção o python na pasta onde está o arquivo web executar o seguinte comando `python -m http.server` por padrão vai subir na porta 8000
- link: https://developer.mozilla.org/pt-BR/docs/Learn/Common_questions/Como_configurar_um_servidor_de_testes_local
- Colocar tbm um endereço no /ect/host -> http://www.algafood.local:8000/ -> 127.0.0.1

- Existe um pré validador, implementado pela especificação do CORS que verifica uma requisição simples, fazendo uma busca simples com o OPTIONS
- a propriedade @CrossOrigin(maxAge = 10) informa o tempo que essa veriifcação deve ficar no cache, nesse exemplo a cada 10s o cache é limpo e se surgir uma requisição não simples
- essa requisição com OPTIONS será feita.


- Ferramenta para disponibilizar logs na nuvem 
- https://douglasdias.loggly.com/search?swicus_org_id=121050693503598592#terms=Started%20AlgafoodApiApplication&from=2021-08-21T18:46:29.348Z&until=2021-08-21T18:56:29.348Z&source_group=





-- Modulo Segurança
URL: authorization code grant
http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://www.algafood.local:8000

Resposta: http://www.algafood.local:8000/?code=W0kei8&state=MC41NzQ1NTAzMzQ4MTY0MDc0


Fluxo authorization code com PKCE no minimo 43 até 128 caracteres

no fluxo do plain o code_Verifier é o mesmo do  code_challenge
code_Verifier: teste123
code_challenge: teste123

http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://www.algafood.local:8000&code_challenge=teste123&code_challenge_method=plain

no fluxo do s256 o code_challenge é o code_Verifier codigo em sha-256 e codificado em base64url
code_Verifier: teste123
code_challenge: KJFg2w2fOfmuF1TE7JwW-QtQ4y4JxftUga5kKz09GjY

a senha secret foi removida para não ser enviada  (Segurança)

http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&redirect_uri=http://www.algafood.local:8000&code_challenge=KJFg2w2fOfmuF1TE7JwW-QtQ4y4JxftUga5kKz09GjY&code_challenge_method=s256


URL: implicit grant
http://localhost:8081/oauth/authorize?response_type=token&client_id=webadmin&state=abc&redirect_uri=http://aplicacao-cliente
resposta: 
http://aplicacao-cliente/#access_token=a447ebb2-f360-47a2-bc95-85e63c14232f&token_type=bearer&state=abc&expires_in=43199&scope=read%20write



##### Docker

###Criar uma rede
docker network ls -> para visualizar rede
docker network create --driver bridge algafood-network

###Criando container mysql passando a rede
docker container run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345 --network algafood-network --name algafood-mysql mysql:8.0

###Gerando imagem da aplicação 
docker image build -t algafood-api .

###Criando container aplicação passando a rede
docker container run --rm -p 8080:8080 -e DB_HOST=algafood-mysql --network algafood-network  algafood-api