Persistencia: Se utiliza serialización con ObjectOutputStream y ObjectInputStream para guardar los eventos en un archivo binario (eventos.dat).

Diagrama de clases: Se puede encontrar el diagrama correspondiente al proyecto dentro de la carpeta UML en formato pdf

Desde la ventana principal de lista de eventos, el botón "Ver Detalle" permite acceder a la gestión de asistentes del evento seleccionado,
allí se puede agregar nuevos asistentes o eliminar asistentes existentes

Se crea una interfaz simple llamada "ActualizableTabla" para que distintas ventanas (como la lista de eventos o los asistentes) puedan implementar un método común: "actualizarTabla()", lo que permite refrescar los datos en pantalla después de modificaciones.
