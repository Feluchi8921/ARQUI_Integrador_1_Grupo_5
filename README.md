# Arquitecturas Web (TUDAI)- Integrador 1

**Descripción: El trabajo consiste en la implementación de un programa que cree el esquema con las siguientes tablas, utilizando JDBC.**
**Características:**
  * Creación de tablas
    * [Cliente: (idCliente int, nombre varchar(500), email varchar(150)]
    * [Factura: (idFactura int, idCliente int)]
    * [Producto (idProducto int, nombre varchar(45), valor float)]
    * [FacturaProducto: (idFactura int, idProducto int, cantidad int)]
  * CRUD de cada clase
  * Método getClientesMayorRecaudacion() que retorne el producto que más recaudó
  * Método obtenerProductoConMayorRecaudacion() que retorna el producto con mayor recaudación
**Tecnologías utilizadas:**
* JAVA, MySQL
* Patrones de arquitecturas web: DAO, Factory Method, Abstract Factory
  
*Autores: Aguerralde Felicitas, De La Torre Giuiliana, Farias Julian, Gramuglia Eliana, Guidi Franco, *

``` Nota: Crear una base con el nombre: integrador1
``` Cambiar el directorio de los archivos .csv
``` Cambiar la contraseña para acceder a la base de datos

  
