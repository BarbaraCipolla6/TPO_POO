
import java.io.Serializable;

public class Asistente implements Serializable {
    private String nombre;
    private String email;

    public Asistente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}
