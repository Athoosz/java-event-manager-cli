# Imagem base com Maven e Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . /app

# Compila o projeto e copia dependências
RUN mvn clean compile dependency:copy-dependencies package

# Imagem final apenas com JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia classes e dependências do estágio de build
COPY --from=build /app/target/classes /app/classes
COPY --from=build /app/target/dependency /app/dependency
COPY --from=build /app/schema.sql /app/

CMD ["java", "-cp", "classes:dependency/*", "com.athoosz.Main"]