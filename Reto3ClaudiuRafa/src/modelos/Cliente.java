package modelos;

/**
 * @author Giovanni
 * @version 0.1
 * @since 12/05/2025
 */
public class Cliente {
    int id;
    String nombre;
    String direccion;
    int codigo;

    /**
     * Constructor completo
     * @param id Id del cliente
     * @param nombre Nombre del cliente
     * @param direccion Direccion del cliente
     * @param codigo Codigo, postal? del cliente
     */
    public Cliente(int id, String nombre, String direccion, int codigo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigo = codigo;
    }

    /**
     * Constructor sin ID. Para agregarlo después
     * @param nombre Nombre del cliente
     * @param direccion Direccion del cliente
     * @param codigo Codigo, postal? del cliente
     */
    public Cliente(String nombre, String direccion, int codigo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigo = codigo;
    }

    /**
     * Constructor vacio
     */
    public Cliente() {}

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", codigo=" + codigo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
