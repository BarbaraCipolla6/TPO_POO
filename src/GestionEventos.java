import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestionEventos implements Serializable{
    private ArrayList<Evento> eventos;
    private static final String ARCHIVO = "eventos.dat";
    

    public GestionEventos(){
        this.eventos = new ArrayList<>();

    }

//Creacion del objeto evento y se agrega a la lista
    public void registrarEvento(int id,LocalDate fecha, String ubicacion, String descripcion){
        Evento nuevoEvento = new Evento(id,fecha, ubicacion,descripcion);
        this.eventos.add(nuevoEvento);
        guardarEventos(this);
        
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

   public static void guardarEventos(GestionEventos gestor){
       // objectI/O para manejo de archivos ------------------ abre el archivo
        //convierte la lista a serie de bytes
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))){
            oos.writeObject(gestor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GestionEventos cargarEventos(){
                            
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))){
            return (GestionEventos) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        
        // Si no existe el archivo o hubo un error, retorna una nueva instancia vacía
        return new GestionEventos();
    }

    public void eliminarEventoPorId(int id) {
        eventos.removeIf(evento -> evento.getId() == id);
        guardarEventos(this);}
        
    public void modificarEvento(int id, LocalDate nuevaFecha, String nuevaUbicacion, String nuevaDescripcion) {
    for (Evento e : eventos) {
        if (e.getId() == id) {
            e.setFecha(nuevaFecha);
            e.setUbicacion(nuevaUbicacion);
            e.setDescripcion(nuevaDescripcion);
            guardarEventos(this);
            return;
        }
    }
}



    
}
