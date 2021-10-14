#Imagem java 
FROM openjdk:16-jdk-slim

#Criar diretorio
WORKDIR /app

# Variavel incluida no plugin do maven
ARG JAR_FILE

#Copiar do primeiro para o segundo
COPY target/${JAR_FILE} /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

#porta que irá rodar interno no docker, não publica a porta
EXPOSE 8080

#Comando de start
CMD ["java", "-jar", "api.jar"]