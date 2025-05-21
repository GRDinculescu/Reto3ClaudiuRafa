package modelos;

import util.Funciones;

import java.time.LocalDate;
import java.util.Locale;

/**
 * @author Giovanni
 * @version 0.1
 * @since 12/05/2025
 */
public class PedidoProducto {
    private int id;
    private Pedido pedido;
    private Producto producto;
    private int unidades;
    private double precio;

    /**
     * Constructor completo
     * @param id Id del pedido por producto
     * @param pedido Objeto pedido
     * @param producto Objeto producto
     * @param unidades Unidades del producto en el pedido
     * @param precio Precio total del producto para ese pedido
     */
    public PedidoProducto(int id, Pedido pedido, Producto producto,
                          int unidades, double precio) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.unidades = unidades;
        this.precio = precio;
    }

    /**
     * Constructor sin ID. Para agregarlo después
     * @param pedido Objeto pedido
     * @param producto Objeto producto
     * @param unidades Unidades del producto en el pedido
     * @param precio Precio total del producto para ese pedido
     */
    public PedidoProducto(Pedido pedido, Producto producto,
                          int unidades, double precio) {
        this.pedido = pedido;
        this.producto = producto;
        this.unidades = unidades;
        this.precio = precio;
    }

    /**
     * Constructor vacio
     */
    public PedidoProducto() {}

    @Override
    public String toString() {
        return "\nFecha: "+ pedido.getFecha() +
                "\nDireccion de envio: "+ pedido.getDireccionEnvio() +
                "\nPrecio total: "+ precio +
                "\nUnidades compradas: "+ unidades +
                "\nNombre del cliente: "+ pedido.getCliente().getNombre() +
                "\nProducto: "+ producto.getNombre() +
                "\nCategoria del producto: "+ producto.getCategoria().getNombre();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
