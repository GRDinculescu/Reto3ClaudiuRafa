package main;

import dao.ClienteDao;
import dao.PedidoDao;
import dao.ProductoDao;
import modelos.Cliente;
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
        List<Producto> productos = ProductoDao.mostrarProductos();
        System.out.println("Productos con bajo stock");
        productos.forEach(System.out::println);

        // Se pide
        int stock = Funciones.dimeEntero("Inserte cuanto stock se subira", sn);

        if (stock > 0){
            for (Producto p : productos){
                ProductoDao.actualizarProducto(p);
            }
        }
    }

    private static void pedidosPorCliente(Scanner sn) {
        int codigo = Funciones.dimeEntero("Inserte codigo de cliente", sn);

        String filter = "where codigo = " + codigo;
        List<Pedido> pedidos = PedidoDao.mostrarPedidos(filter);

        if (!pedidos.isEmpty()){
            pedidos.forEach(System.out::println);
        } else {
            System.out.println("No tiene pedidos");
        }
    }

    private static void productosMasVendidos(Scanner sn) {

    }
}
