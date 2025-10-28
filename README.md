# FASE 01

## Primeros pasos

En esta primera fase pretende crear una pequeña aplicación de base de datos que simule la
gestión de productos de una tienda. De momento, la única entidad que se va a crear es la de los productos.
Ya en la siguiente fase se añadirá otra entidad para expandir un poco el esquema de la base de datos.

Para manejar los datos de la entidad **Product**, que es como la llamaremos en el modelo de la tabla, emplearemos
una interfaz que herede JPARepository, el servicio y el controlador.

También cabe aclarar que durante esta fase y la siguiente del proyecto, se utilizará una base de datos simulada en H2. Ya más adelante se migrará la aplicación a una base de datos real.
