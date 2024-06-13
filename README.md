# CofradiAPP

## Descripción

CofradiAPP es una aplicación sobre la Semana Santa de Sevilla en la cual encontrarás los
datos de todas las hermandades, y además cuenta con un juego de cartas coleccionables
sobre la misma.

Podrás elegir tus hermandades favoritas, verlas en detalle, ver los pasos, imágenes,
bandas, etc.

Acerca de las cards, estas podrán obtenerse a través de códigos QR repartidos por las
distintas iglesias de la ciudad, casas de hermandad, monumentos, calles y sitios icónicos de
la Semana Santa.

## Despliegue

Para desplegar la aplicacion necesitaremos tener instalado:
-Docker
-JDK 17
-Node JS
-Angular CLI 17
-Flutter
-Android SDK

Para desplegar la aplicacion primero deberiamos ejecutar el Docker-Compose que se encuentra en el proyecto de la API.
Luego nos iriamos al proyecto para ejecutar el comando mnv spring-boot:run y se iniciaria la API.
Para iniciar la aplicacion web solamente necesitariamos ejecutar el comando ng serve dentro del directorio del protecto de Angular.
Para iniciar la aplicacion movil solamente necesitariamos ejecutar el comando flutter run en el directorio del proyecto de Flutter.

## Pruebas

Si desea probar la API cuenta con una coleccion de postman dentro del proyecto de la API para que pueda probar todos sus endpoints.
