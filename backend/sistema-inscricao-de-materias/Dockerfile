#* Building project with Maven
#Pegando o maven  e usando ele como build
FROM maven:3.8.5-openjdk-17 AS build

# Copiando a pasta SRC da aplicação para uma pasta SRC dentro do container do Docker
COPY src /app/src
COPY pom.xml /app

#Configurando como diretório padrão
WORKDIR /app

#Instalando as dependências
RUN mvn clean install -e -DskipTests

#* Run JRE
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]