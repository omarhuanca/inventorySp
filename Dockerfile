# Usa una imagen base de OpenJDK
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación en el contenedor
COPY target/inventory.jar inventory.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "inventory.jar"]
