# FASE 02

## Añadiendo registry-service

Comenzando esta segunda fase del proyecto, vamos a empezar a despedazar el proyecto **store** en pequeños proyectos (servicios) para ir transformando lentamente el proyecto monolítico en un proyecto de microservicios.
Para ello, el primer paso que vamos a dar es añadir un proyecto llamada **registry-server**, cuya dependencia principal es la de **Eureka Server** (FALTA PONER ENLACE).

Eureka Server nos permitirá registrar los servicios de la aplicación.

# FASE 01

## Excepciones básicas

Para tener un control de errores más personalizado, se han creado de momento unas cuantas excepciones:

- CategoryNotFoundException
- ProductNotFoundException
- CategoryAlreadyExistsException
- ProductAlreadyExistsException

De momento solo lanzan un mensaje de error para ayudar a localizar cualquier error en ejecución, pero más adelante se desarrollarán más estas excepciones y otras que se vayan creando.

También se han añadido varios tests para Product y Category, aunque de momento están incompletos ya que faltan los de update y delete.

## Relación Product y Category

En esta etapa de la fase 1 se va a implementar todo lo relacionado con la entidad Category: repositorio, servicio, controlador y DTOs.
Además, se va a establecer la relación OneToMany entre Product y Category, siendo las instancias de esta última las que tendrán una lista
con los productos.

## Primeros pasos

En esta primera fase se pretende crear una pequeña aplicación de base de datos que simule la
gestión de productos de una tienda. De momento, la única entidad que se va a crear es la de los productos.
Ya en la siguiente fase se añadirá otra entidad para expandir un poco el esquema de la base de datos.

Para manejar los datos de la entidad **Product**, que es como la llamaremos en el modelo de la tabla, emplearemos
una interfaz que herede JPARepository, el servicio y el controlador.

También cabe aclarar que durante esta fase y la siguiente del proyecto, se utilizará una base de datos simulada en H2. Ya más adelante se migrará la aplicación a una base de datos real.
