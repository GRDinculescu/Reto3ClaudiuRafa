package modelos;

/**
 * @author Giovanni
 * @version 0.1
 * @since 12/05/2025
 */
public class Producto {
    private int id;
    private Categoria categoria;
    private String nombre;
    private double precio;
    private String descripcion;
    private String color;
    private String talla;
    private int stock;

    /**
     * Constructor completo
     * @param id Id del producto
     * @param categoria Categoria del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param descripcion Descripcion del producto
     * @param color Color del producto
     * @param talla Talla del producto
     * @param stock Cantidad del producto en stock
     */
    public Producto(int id, Categoria categoria, String nombre,
                   double precio, String descripcion, String color,
                   String talla, int stock) {

        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.color = color;
        this.talla = talla;
        this.stock = stock;
    }

    /**
     * Constructor sin ID. Para ponerlo después
     * @param categoria Categoria del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param descripcion Descripcion del producto
     * @param color Color del producto
     * @param talla Talla del producto
     * @param stock Cantidad del producto en stock
     */
    public Producto(Categoria categoria, String nombre, double precio,
                    String descripcion, String color, String talla, int stock) {

        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.color = color;
        this.talla = talla;
        this.stock = stock;
    }

    /**
     * Constructor vacío
     */
    public Producto() {}

    @Override
    public String toString() {
        return "Producto: " +
                "Nombre='" + nombre + '\'' +
                " | Precio=" + precio +
                "$ | Descripcion='" + descripcion + '\'' +
                " | Color='" + color + '\'' +
                " | Talla='" + talla + '\'' +
                " | Stock=" + stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
