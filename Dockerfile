# Imagem base com Java 17
FROM eclipse-temurin:17-jre

# Diretorio de trabalho dentro do container
WORKDIR /app

# Copia as classes compiladas e dependencias
COPY target/classes /app/classes
COPY target/dependency /app/dependency

# Copia o arquivo do banco de dados SQLite (via volume)
COPY schema.sql /app/

# Comando para rodar a aplicacao
CMD ["java", "-cp", "classes:dependency/*", "com.athoosz.Main"]
