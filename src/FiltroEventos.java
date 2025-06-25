import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroEventos {

    public static List<Evento> eventosFuturos(List<Evento> eventos) {
        return eventos.stream()
                      .filter(e -> e.getFecha().isAfter(LocalDate.now()))
                      .collect(Collectors.toList());
    }

    public static List<Evento> eventosPasados(List<Evento> eventos) {
        return eventos.stream()
                      .filter(e -> e.getFecha().isBefore(LocalDate.now()))
                      .collect(Collectors.toList());
    }

    public static List<Evento> eventosDeHoy(List<Evento> eventos) {
        return eventos.stream()
                      .filter(e -> e.getFecha().isEqual(LocalDate.now()))
                      .collect(Collectors.toList());
    }
}
