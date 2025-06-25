import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Lista extends JFrame implements ActualizableTabla{
    private DefaultTableModel model;
    private GestionEventos gestor;
    

    public Lista(GestionEventos gestor){
        this.gestor = gestor; 

        setTitle("Lista Eventos");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    Lista.this,
                    "¿Estás segura/o de que querés salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        model = new DefaultTableModel(new String[]{"ID", "Fecha", "Ubicación", "Descripción"}, 0);
        JTable table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

       
        header.setBackground(new Color(36, 62, 54)); 
        header.setForeground(Color.WHITE); 
        header.setFont(new Font("Arial", Font.BOLD, 14));
        table.setBackground(new Color(97, 152, 142));
        table.setForeground(Color.WHITE); 
        table.setGridColor(new Color (160, 178, 166));
        

        JMenuBar menuBar = new JMenuBar();
        JMenu menuVer = new JMenu("Ver más...");

        JMenuItem itemFuturos = new JMenuItem("Eventos Futuros");
        itemFuturos.addActionListener(e -> {
            List<Evento> futuros = FiltroEventos.eventosFuturos(gestor.getEventos());
            new VentanaFiltradaEventos("Eventos Futuros", futuros);
        });

        JMenuItem itemPasados = new JMenuItem("Eventos Pasados");
        itemPasados.addActionListener(e -> {
            List<Evento> pasados = FiltroEventos.eventosPasados(gestor.getEventos());
            new VentanaFiltradaEventos("Eventos Pasados", pasados);
        });

        menuVer.add(itemFuturos);
        menuVer.add(itemPasados);
        menuBar.add(menuVer);

        setJMenuBar(menuBar);

        JButton btnAgregar = new JButton("Agregar Evento");
        btnAgregar.addActionListener(e ->{
            new VentanaEventos(gestor, this);
        });

        JButton btnModificar = new JButton("Modificar Evento");
        btnModificar.addActionListener(e -> {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) model.getValueAt(filaSeleccionada, 0);
            LocalDate fecha = (LocalDate) model.getValueAt(filaSeleccionada, 1);
//Se define un formateador para trabajar con fechas en formato dia/mes/año, para que coincida con el formato que se le muestra al usuario
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fecha.format(formatter);
            JTextField campoFecha = new JTextField(fechaFormateada);
            String ubicacion = (String) model.getValueAt(filaSeleccionada, 2);
            String descripcion = (String) model.getValueAt(filaSeleccionada, 3);

            // Muestra un pequeño form de edición 
            
            JTextField campoUbicacion = new JTextField(ubicacion);
            JTextField campoDescripcion = new JTextField(descripcion);

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Fecha:"));
            panel.add(campoFecha);
            panel.add(new JLabel("Ubicación:"));
            panel.add(campoUbicacion);
            panel.add(new JLabel("Descripción:"));
            panel.add(campoDescripcion);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Modificar Evento", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                String nuevaFechaStr = campoFecha.getText();
                LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr, formatter);
                String nuevaUbicacion = campoUbicacion.getText();
                String nuevaDescripcion = campoDescripcion.getText();

                gestor.modificarEvento(id, nuevaFecha, nuevaUbicacion, nuevaDescripcion);
                actualizarTabla();
            }

            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un evento primero.");
            }
        });

        
        JButton btnEliminar = new JButton("Eliminar Evento");
        btnEliminar.addActionListener(e -> {
        int filaSeleccionada = table.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás segura/o de eliminar este evento?", "Confirmar", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int idEvento = (int) model.getValueAt(filaSeleccionada, 0); // tomamos el ID de la fila
                gestor.eliminarEventoPorId(idEvento);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Evento eliminado con éxito.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccioná un evento primero.");
        }
        });
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.addActionListener(e -> {
            int filaSeleccionada = table.getSelectedRow();

            if (filaSeleccionada >= 0) {
                int id = (int) model.getValueAt(filaSeleccionada, 0);

                // Buscar el evento por ID
                Evento eventoSeleccionado = null;
                for (Evento ev : gestor.getEventos()) {
                    if (ev.getId() == id) {
                        eventoSeleccionado = ev;
                        break;
                    }
                }

                if (eventoSeleccionado != null) {
                    new VentanaAsistencia(gestor,eventoSeleccionado);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un evento primero.");
            }
        });

     

        
        for(Evento evento: gestor.getEventos()){
            model.addRow(new Object[]{
                evento.getId(),
                evento.getFecha(), 
                evento.getUbicacion(), 
                evento.getDescripcion()
            });
        }

   
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(97, 152, 142));
        
        


        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalle);

        btnAgregar.setBackground(new Color(36, 62, 54));
        btnModificar.setBackground(new Color(36, 62, 54));
        btnEliminar.setBackground(new Color(36, 62, 54));
        btnAgregar.setForeground(Color.white);
        btnModificar.setForeground(Color.white);
        btnEliminar.setForeground(Color.white);
        btnVerDetalle.setBackground(new Color(36, 62, 54));
        btnVerDetalle.setForeground(Color.white);
        panelPrincipal.add(new JScrollPane(table), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
        
        pack();
    }

    @Override
    public void actualizarTabla() {
        model.setRowCount(0); // borra las filas
        for (Evento evento : gestor.getEventos()) {  // asumimos la variable gestor
            model.addRow(new Object[]{
                evento.getId(),
                evento.getFecha(),
                evento.getUbicacion(),
                evento.getDescripcion()
            });
        }
    }
}