package modelos;

import java.util.Date;

/**
 * @author Giovanni
 * @version 0.1
 * @since 12/05/2025
 */
public class Pedido {
    int id;
    Cliente cliente;
    double precioTotal;
    String direccionEnvio;
    Date fecha;

    /**
     * Constructor completo
     * @param id Id del pedido
     * @param cliente Cliente que hace el pedido
     * @param precioTotal Precio total del pedido
     * @param direccionEnvio Direccion de envío
     * @param fecha Fecha en la que se realizo el pedido
     */
    public Pedido(int id, Cliente cliente, double precioTotal,
                  String direccionEnvio, Date fecha) {
        this.id = id;
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        this.direccionEnvio = direccionEnvio;
        this.fecha = fecha;
    }

    /**
     * Constructor sin ID. Para agregarlo después
     * @param cliente Cliente que hace el pedido
     * @param precioTotal Precio total del pedido
     * @param direccionEnvio Direccion de envío
     * @param fecha Fecha en la que se realizo el pedido
     */
    public Pedido(Cliente cliente, double precioTotal, String direccionEnvio, Date fecha) {
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        this.direccionEnvio = direccionEnvio;
        this.fecha = fecha;
    }

    /**
     * Constructor vacio
     */
    public Pedido() {}

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", precioTotal=" + precioTotal +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
