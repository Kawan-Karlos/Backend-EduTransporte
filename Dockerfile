# Etapa 1: Build da aplicacao com Maven e JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia pom.xml e baixa dependencias para cache eficiente
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o codigo fonte e compila o pacote .jar (pulando testes no build de container)
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final enxuta para execucao com JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Expõe a porta padrao do Spring Boot
EXPOSE 8080

# Copia o jar gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Executa a aplicacao
ENTRYPOINT ["java", "-jar", "app.jar"]

