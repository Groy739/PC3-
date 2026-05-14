
package agencia.modelos;


import java.io.Serializable;
import java.time.LocalDateTime;

public class TicketViaje implements Serializable, Comparable<TicketViaje> {
    private static final long serialVersionUID = 1L;
    
    private String numeroTicket;
    private String dpi;
    private String tipo;
    private int nivelPrioridad;
    private LocalDateTime horaIngreso;
    
    public TicketViaje(String numeroTicket, String dpi, String tipo, int nivelPrioridad) {
        this.numeroTicket = numeroTicket;
        this.dpi = dpi;
        this.tipo = tipo;
        this.nivelPrioridad = nivelPrioridad;
        this.horaIngreso = LocalDateTime.now();
    }

    public String getNumeroTicket() { return numeroTicket; }
    public String getDpi() { return dpi; }
    public String getTipo() { return tipo; }
    public LocalDateTime getHoraIngreso() { return horaIngreso; }

    @Override
    public int compareTo(TicketViaje otro) {
        if (this.nivelPrioridad != otro.nivelPrioridad) {
            return Integer.compare(otro.nivelPrioridad, this.nivelPrioridad); 
        }
        return this.horaIngreso.compareTo(otro.horaIngreso);
    }
}
