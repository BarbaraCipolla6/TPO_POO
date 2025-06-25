import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAsistencia extends JFrame implements ActualizableTabla {
    private GestionEventos gestor;
    private Evento evento; // el evento al que pertenecen los asistentes
    private DefaultTableModel model;
    private JTable tabla;

    public VentanaAsistencia(GestionEventos gestor,Evento evento) {
        this.evento = evento;

        setTitle("Asistentes al Evento: " + evento.getDescripcion());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"Nombre", "Email"}, 0);
        tabla = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton btnAgregar = new JButton("Agregar Asistente");
        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());

        JButton btnEliminar = new JButton("Eliminar Asistente");
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow(); //JTable de asistentes

            if (filaSeleccionada >= 0) {
                String nombre = (String) model.getValueAt(filaSeleccionada, 0); // Suponiendo que columna 0 = nombre

                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar a " + nombre + " de la lista de asistentes?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    evento.eliminarAsistentePorNombre(nombre); 
                    actualizarTabla();
                    GestionEventos.guardarEventos(gestor); // guardás cambios
                    JOptionPane.showMessageDialog(this, "Asistente eliminado con éxito.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un asistente primero.");
            }
        });



        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarTabla();
        setVisible(true);
    }

    @Override
    public void actualizarTabla() {
        model.setRowCount(0);
        List<Asistente> asistentes = evento.getAsistentes(); 
        for (Asistente a : asistentes) {
            model.addRow(new Object[]{a.getNombre(), a.getEmail()});
        }
    }

    private void mostrarFormularioAgregar() {
        JTextField campoNombre = new JTextField(15);
        JTextField campoEmail = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Email:"));
        panel.add(campoEmail);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Nuevo Asistente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText();
            String email = campoEmail.getText();

            if (!nombre.isEmpty() && !email.isEmpty()) {
                evento.agregarAsistente(new Asistente(nombre, email)); 
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Completá ambos campos.");
            }
        }
    }

     

}
