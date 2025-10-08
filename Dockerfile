# Imagen base con JDK 21
FROM eclipse-temurin:21-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el código fuente y empaqueta la app
COPY . .

# Compila el proyecto (Render tiene Maven instalado, pero por seguridad lo hacemos desde la imagen)
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080 (Render lo necesita para mapear tráfico)
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-jar", "target/inventorybackend-0.0.1-SNAPSHOT.jar"]
