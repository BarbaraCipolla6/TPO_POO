public class App {
    public static void main(String[] args) {
        GestionEventos gestor = GestionEventos.cargarEventos();
       
        
        Lista listaVentana = new Lista(gestor);
        listaVentana.setVisible(true);
        
        VentanaEventos ventanaEventos = new VentanaEventos(gestor, listaVentana);
        ventanaEventos.setVisible(true);
    }
}
