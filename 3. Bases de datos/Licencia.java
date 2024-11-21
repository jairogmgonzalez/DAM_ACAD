
import java.sql.Timestamp;

public class Licencia{
    
    private String tipo;
    private Timestamp expedicion;
    private Timestamp caducidad;

    // Constructor por parámetros
    public Licencia(String tipo, Timestamp expedicion, Timestamp caducidad){
        this.tipo = tipo;
        this.expedicion = expedicion;
        this.caducidad = caducidad;
    }

    // Getters y setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(Timestamp expedicion) {
        this.expedicion = expedicion;
    }

    public Timestamp getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Timestamp caducidad) {
        this.caducidad = caducidad;
    }

}