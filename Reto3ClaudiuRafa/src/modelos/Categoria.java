package modelos;

/**
 * @author Giovanni
 * @version 0.1
 * @since 12/05/2025
 */
public class Categoria {
    private int id;
    private String nombre;

    /**
     * Constructor completo
     * @param id Id de la categoria
     * @param nombre Nombre de la categoria
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Constructor sin ID. Para establecerlo mas tarde
     * @param nombre Nombre de la categoria
     */
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor vacío
     */
    public Categoria() {}

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
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
}
