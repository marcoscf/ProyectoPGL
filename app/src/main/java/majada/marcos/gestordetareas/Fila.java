package majada.marcos.gestordetareas;

import android.support.v7.app.AppCompatActivity;

/**
 * Esta clase indica los campos que debe tener una fila del ListView.
 */

public class Fila extends AppCompatActivity {
    private String nombre, estado, prioridad, fecha, hora;
    private int id;

    public Fila(int id, String nombre, String estado, String prioridad, String fecha, String hora) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.hora = hora;
    }

    //Solo ponemos los getters y setters que vayamos a usar.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

}
