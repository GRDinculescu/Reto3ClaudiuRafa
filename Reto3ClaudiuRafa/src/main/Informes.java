package main;

import dao.PedidoProductoDao;
import dao.ProductoDao;
import modelos.PedidoProducto;
import modelos.Producto;
import util.Funciones;

import java.util.List;
import java.util.Scanner;

/**
 * @author Giovanni
 * @version 0.3.0
 * @since 14/0.5/2025
 */
public class Informes {
    /**
     * Muestra el sub-menu de Informes
     * @param sn Scanner para pasar a otras funciones o preguntar la opcion
     */
    public static void menu(Scanner sn){
        System.out.println("\n=== Informes ===");
        String menu = """
                
                Elige opcion:
                1. Bajo stock
                2. Pedidos por cliente
                3. Productos mas vendidos
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0) return;

            switch (op){
                case 1 -> bajoStock(sn);
                case 2 -> pedidosPorCliente(sn);
                case 3 -> productosMasVendidos();
                default -> System.err.println("--- Opcion invalida ---");
            }
        }
    }

    /**
     * Busca los productos con bajo stock
     * @param sn Scanner para preguntar cuanto stock meter
     */
    private static void bajoStock(Scanner sn) {
        System.out.println("\n=== Bajo stock ===");
        String filtro = "where stock < 5";

        // Se obtienen los productos y se muestran
        List<Producto> productos = ProductoDao.mostrarProductos("where stock < 5");

        if (!productos.isEmpty()) {
            System.out.println("Productos con bajo stock");
            for (Producto p : productos){
                // Si el stock del producto es menor a 5
                if (p.getStock() < 5) {
                    // Muestra el producto
                    System.out.println(p);

                    if (Funciones.dimeSiONo("¿Quieres actualizar el stock?", sn)) {
                        int stock = Funciones.dimeEntero("Inserte cuanto stock se subira", sn);

                        while (stock <= 0){
                            System.err.println("El stock debe ser mayor a 0");
                            stock = Funciones.dimeEntero("Inserte cuanto stock se subira", sn);
                        }

                        // Actualiza el stock solo a ese producto
                        String filter = "stock = stock + " + stock + " where stock < 5 and idProducto = " + p.getId();
                        ProductoDao.actualizarProducto(filter);
                    }
                }
            }
        } else {
            System.out.println("No hay productos con bajo stock\n");
        }
    }

    /**
     * Muestra los pedidos de un cliente
     * @param sn Scanner para pedir el CODIGO, no ID
     */
    private static void pedidosPorCliente(Scanner sn) {
        System.out.println("\n=== Pedidos por cliente ===");
        int codigo;

        do {
            codigo = Funciones.dimeEntero("Inserte codigo de cliente", sn);
        } while (!Funciones.dimeSiONo("¿Es ese el codigo?", sn));

        // Obtener los pedidos del cliente
        List<PedidoProducto> pedidoProductos = PedidoProductoDao.mostrarPedidoProductosPorCodigoCliente(codigo);

        // Mostrar los pedidos
        PedidoProductoDao.mostrarPedidos(pedidoProductos);
    }

    /**
     * Muestra los productos mas vendidos, aun si hay empate
     */
    private static void productosMasVendidos() {
        System.out.println("\n=== Productos mas vendidos ===");
        List<Producto> productos = ProductoDao.mostrarProductosMasComprados();

        if (!productos.isEmpty()){
            System.out.println("Productos mas comprados");
            productos.forEach(System.out::println); // Muestra los productos mas vendidos/comprados
        } else {
            System.out.println("Aun no se comprado ningún producto");
        }

        System.out.println();
    }
}
