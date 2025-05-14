package main;

import dao.CategoriaDao;
import dao.ClienteDao;
import dao.ProductoDao;
import modelos.Categoria;
import modelos.Cliente;
import modelos.Producto;
import util.Funciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mantenimientos {
    public static void menu(Scanner sn){
        String menu = """
                Elige opcion:
                1. Gestion de categorías
                2. Gestion de productos
                3. Gestion de clientes
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0){
                return;
            }

            switch (op){
                case 1 -> {
                    System.out.println("=== Gestion de categorías ===");
                    gestionCategorias(sn);
                }
                case 2 -> {
                    System.out.println("=== Gestion de productos ===");
                    gestionProductos(sn);
                }
                case 3 -> {
                    System.out.println("=== Gestion de clientes ===");
                    gestionClientes(sn);
                }
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    private static void gestionCategorias(Scanner sn){
        Categoria categoria = new Categoria(
                Funciones.dimeString(
                        "Inserte el nombre de nueva categoria: ",
                        sn));
        CategoriaDao.insertarCategoria(categoria);
    }

    private static void gestionProductos(Scanner sn){
        List<Categoria> categorias = CategoriaDao.mostrarCategorias();

        if (!categorias.isEmpty()) {
            Producto producto = new Producto();
            producto.setNombre(Funciones.dimeString("Inserte color", sn));
            producto.setPrecio(Funciones.dimeDouble("Inserte color", sn));
            producto.setDescripcion(Funciones.dimeString("Inserte color", sn));
            producto.setColor(Funciones.dimeString("Inserte color", sn));
            producto.setTalla(Funciones.dimeString("Inserte color", sn));
            producto.setStock(Funciones.dimeEntero("Inserte color", sn));

            Categoria categoria = null;


            do {
                categorias.forEach(System.out::println);
                int idCategoria = Funciones.dimeEntero("Seleccione categoria por ID", sn);

                for (Categoria c : categorias) {
                    if (c.getId() == idCategoria) {
                        categoria = c;
                        break;
                    }
                }
            } while (categoria == null);

            producto.setCategoria(categoria);

            ProductoDao.insertarProducto(producto);
        } else {
            System.out.println("No hay categorias. Crea una primero.");
        }
    }

    private static void gestionClientes(Scanner sn){
        String menu = """
                Elige opcion:
                1. Alta de nuevo cliente
                2. Busqueda por codigo
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0) return;

            switch (op){
                case 1 -> {
                    System.out.println("=== Alta de nuevo cliente ===");
                    altaCliente(sn);
                }
                case 2 -> {
                    System.out.println("=== Busqueda por codigo ===");
                    busquedaCodigo(sn);
                }
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    private static void altaCliente(Scanner sn){
        Cliente cliente = new Cliente();

        // Obtenemos todos los clientes para obtener sus codigos
        List<Cliente> clientes = ClienteDao.mostrarClientes();
        List<Integer> codigos = new ArrayList<>();
        for (Cliente c : clientes){
            codigos.add(c.getCodigo());
        }

        cliente.setNombre(Funciones.dimeString("Inserte nombre", sn));
        cliente.setDireccion(Funciones.dimeString("Inserte direccion", sn));

        // Validar codigo
        int codigo;
        do {
            codigo = Funciones.dimeEntero("Inserte codigo", sn);
        } while (codigos.contains(codigo));
        cliente.setCodigo(codigo);

        ClienteDao.insertarCliente(cliente);
    }

    private static void busquedaCodigo(Scanner sn){

    }
}
