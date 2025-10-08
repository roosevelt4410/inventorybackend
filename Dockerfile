# Etapa 1: Build (compilación del proyecto)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiar todo el código fuente
COPY . .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Compilar y empaquetar el proyecto (sin ejecutar tests)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime (ejecución de la app)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el .jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
