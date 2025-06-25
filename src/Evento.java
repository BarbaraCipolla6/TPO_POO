import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Evento implements Serializable{
    private int id;
    private String ubicacion,descripcion;
    private LocalDate fecha;
    private List<Asistente> asistentes = new ArrayList<>();
    
    public Evento(int id,LocalDate fecha, String ubicacion, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString(){
        return "Fecha: "+ fecha + 
                "Ubicación: " + ubicacion +
                "Descripción: " + descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void agregarAsistente(Asistente a) {
    asistentes.add(a);
}

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    public void eliminarAsistentePorNombre(String nombre) {
    asistentes.removeIf(a -> a.getNombre().equalsIgnoreCase(nombre));
}

}
