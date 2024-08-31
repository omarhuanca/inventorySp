# Inventory Spring Boot

## Requisitos

* Docker version 26.0.1 o superior


## Ejecución

**Uso de una imagen docker**

El uso de una imagen ayuda a no tener que instalar un entorno
de trabajo local.


**Creación de imagen y ejecución**

Se utiliza docker para poder generar la ejecución de la aplicación
en un entorno de trabajo externo.

```
$ docker build -t spring-boot .

```

Ejecución de la imagen

```
$ docker run --net=host -p 8080:8080 spring-boot

```
