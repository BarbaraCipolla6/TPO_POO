import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;



public class VentanaEventos extends JFrame {
    // atributos de VE
    private GestionEventos gestor;
    private JTextField campoID, campoUbicacion, campoDescripcion;
    private JFormattedTextField campoFecha;
    private JButton btnRegistrar;
    private Lista listaVentana;

    public VentanaEventos(GestionEventos gestor, Lista listaVentana) {
        this.gestor = gestor;
        this.listaVentana = listaVentana;

        
        setTitle("Gestión de Eventos:");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con GridLayout
        JPanel panelPrincipal = new JPanel(new GridLayout(5, 2, 10, 10));
        panelPrincipal.setBackground(new Color(160, 178, 166)); // A0B2A6 hexa



        panelPrincipal.add(new JLabel("ID:"));
        campoID = new JTextField(15);
        panelPrincipal.add(campoID);

        panelPrincipal.add(new JLabel("Fecha (dd/MM/yyyy):"));
        try {
            MaskFormatter formatoFecha = new MaskFormatter("##/##/####");
            formatoFecha.setPlaceholderCharacter('_');
            campoFecha = new JFormattedTextField(formatoFecha);
            campoFecha.setColumns(15);
        } catch (Exception ex) {
            campoFecha = new JFormattedTextField();
        }
        panelPrincipal.add(campoFecha);

        panelPrincipal.add(new JLabel("Ubicación:"));
        campoUbicacion = new JTextField(15);
        panelPrincipal.add(campoUbicacion);

        panelPrincipal.add(new JLabel("Descripción:"));
        campoDescripcion = new JTextField(15);
        panelPrincipal.add(campoDescripcion);

        
        
        // Botón de registrar
        panelPrincipal.add(new JLabel("")); // espacio vacío para acomodar el boton registro a la derecha
        btnRegistrar = new JButton("Registrar evento");
        btnRegistrar.setBackground(new Color(97, 152, 142)); // 61988E hexa
        btnRegistrar.setForeground(Color.white);
        panelPrincipal.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
        String idText = campoID.getText();
        
        String fecha = campoFecha.getText();
        String ubicacion = campoUbicacion.getText();
        String descripcion = campoDescripcion.getText();

         if (!idText.isEmpty() && !fecha.isEmpty() && !ubicacion.isEmpty() && !descripcion.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaEvento = LocalDate.parse(fecha, formatter);

                    gestor.registrarEvento(id, fechaEvento, ubicacion, descripcion);
                    JOptionPane.showMessageDialog(this, "Evento registrado exitosamente");
                    listaVentana.actualizarTabla();

                    campoID.setText("");
                    campoFecha.setText("");
                    campoUbicacion.setText("");
                    campoDescripcion.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número válido", "Error",JOptionPane.ERROR_MESSAGE);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "La fecha debe tener el formato dd/MM/yyyy","Error",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Completá todos los campos.", "Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panelPrincipal);
        pack(); // ajusta la ventana al contenido
        setVisible(true);
    }
}
