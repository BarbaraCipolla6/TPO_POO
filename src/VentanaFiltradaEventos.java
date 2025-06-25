import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaFiltradaEventos extends JFrame {
    public VentanaFiltradaEventos(String titulo, List<Evento> eventos) {
        setTitle(titulo);
        setSize(400, 300);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Fecha", "Ubicación", "Descripción"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Evento evento : eventos) {
            model.addRow(new Object[]{
                evento.getId(),
                evento.getFecha(),
                evento.getUbicacion(),
                evento.getDescripcion()
            });
        }

        JTable tabla = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
