package main;

import dao.PedidoDao;
import dao.ProductoDao;
import modelos.Pedido;
import modelos.Producto;
import util.Funciones;

import java.util.List;
import java.util.Scanner;

public class Informes {
    public static void menu(Scanner sn){
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
                case 1 -> {
                    System.out.println("=== Bajo stock ===");
                    bajoStock(sn);
                }
                case 2 -> {
                    System.out.println("=== Pedidos por cliente ===");
                    pedidosPorCliente(sn);
                }
                case 3 -> {
                    System.out.println("=== Productos mas vendidos ===");
                    productosMasVendidos(sn);
                }
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    private static void bajoStock(Scanner sn) {
        String filtro = "where stock < 5";

        // Se obtienen los productos y se muestran
        List<Producto> productos = ProductoDao.mostrarProductos("where stock < 5");

        if (!productos.isEmpty()) {
            System.out.println("Productos con bajo stock");
            for (Producto p : productos){
                if (p.getStock() < 5) System.out.println(p);
            }

            // Se pide
            int stock = Funciones.dimeEntero("Inserte cuanto stock se subira", sn);

            if (stock > 0) {
                String filter = "stock = stock + " + stock + " where stock < 5";
                ProductoDao.actualizarProducto(filter);
            }
        } else {
            System.out.println("No hay productos con bajo stock\n");
        }
    }

    private static void pedidosPorCliente(Scanner sn) {
        int codigo = Funciones.dimeEntero("Inserte codigo de cliente", sn);

        List<Pedido> pedidos = PedidoDao.mostrarPedidosPorCodigoCliente(codigo);

        if (!pedidos.isEmpty()){
            pedidos.forEach(System.out::println);
        } else {
            System.out.println("El cliente no tiene pedidos\n");
        }
    }

    private static void productosMasVendidos(Scanner sn) {
        List<Producto> productos = ProductoDao.mostrarProductosMasComprados();

        if (!productos.isEmpty()){
            System.out.println("Productos mas comprados");
            productos.forEach(System.out::println);
        } else {
            System.out.println("Aun no se comprado ningún producto");
        }

        System.out.println();
    }
}
