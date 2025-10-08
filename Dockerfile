# Etapa 1: Build (compilación del proyecto)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiar todo el código fuente
COPY . .

# Dar permisos al wrapper de Maven
RUN chmod +x mvnw

# Compilar sin ejecutar tests ni intentar conectarse a la DB
RUN ./mvnw clean package -DskipTests -Dspring.datasource.url=jdbc:postgresql://localhost:5432/dummydb

# Etapa 2: Runtime (ejecución de la app)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el .jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando de inicio usando variables de entorno de Render
ENTRYPOINT ["sh", "-c", "java -Dspring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME} -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASSWORD} -jar app.jar"]
