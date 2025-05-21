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


/**
 * @author Giovanni
 * @version 0.1.5
 * @since 13/05/2025
 */
public class Mantenimientos {
    /**
     * Muestra el sub-menu de mantenimientos
     * @param sn Scanner para preguntar las opciones y pasar a funciones
     */
    public static void menu(Scanner sn){
        System.out.println("\n=== Mantenimiento ===");
        String menu = """
                Elige opcion:
                1. Gestion de categorías
                2. Gestion de productos
                3. Gestion de clientes
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0) return;

            switch (op){
                case 1 -> gestionCategorias(sn);
                case 2 -> gestionProductos(sn);
                case 3 -> gestionClientes(sn);
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    /**
     * Inserta una nueva categoria
     * @param sn Scanner para preguntar los datos de la categoria
     */
    private static void gestionCategorias(Scanner sn){
        System.out.println("\n=== Gestion de categorías ===");
        Categoria categoria = new Categoria(
                Funciones.dimeString(
                        "Inserte el nombre de nueva categoria: ",
                        sn));
        CategoriaDao.insertarCategoria(categoria);
        System.out.println();
    }

    /**
     * Inserta un nuevo producto
     * @param sn Scanner para preguntar los datos del producto
     */
    private static void gestionProductos(Scanner sn){
        System.out.println("\n=== Gestion de productos ===");
        List<Categoria> categorias = CategoriaDao.mostrarCategorias();

        if (!categorias.isEmpty()) {
            Producto producto = new Producto();
            producto.setNombre(Funciones.dimeString("Inserte nombre", sn));
            producto.setPrecio(Funciones.dimeDouble("Inserte precio", sn));
            producto.setDescripcion(Funciones.dimeString("Inserte descripcion", sn));
            producto.setColor(Funciones.dimeString("Inserte color", sn));
            producto.setTalla(Funciones.dimeString("Inserte talla", sn));
            producto.setStock(Funciones.dimeEntero("Inserte stock", sn));

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

    /**
     * Muestra el sub-menu para gestionar los clientes
     * @param sn Scanner para preguntar la opcion y pasar a funciones
     */
    private static void gestionClientes(Scanner sn){
        System.out.println("\n=== Gestion de clientes ===");
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
                case 1 -> altaCliente(sn);
                case 2 -> busquedaCodigo(sn);
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    /**
     * Inserta un nuevo cliente
     * @param sn Scanner para preguntar los datos
     */
    private static void altaCliente(Scanner sn){
        System.out.println("\n=== Alta de nuevo cliente ===");
        Cliente cliente = new Cliente();

        // Obtenemos todos los clientes para obtener sus codigos
        List<Cliente> clientes = ClienteDao.mostrarClientes();
        List<Integer> codigos = new ArrayList<>();
        for (Cliente c : clientes) codigos.add(c.getCodigo());

        cliente.setNombre(Funciones.dimeString("Inserte nombre", sn));
        cliente.setDireccion(Funciones.dimeString("Inserte direccion", sn));

        // Validar que el codigo no esta en la tabla
        int codigo;
        do {
            codigo = Funciones.dimeEntero("Inserte codigo", sn);
        } while (codigos.contains(codigo));
        cliente.setCodigo(codigo);

        ClienteDao.insertarCliente(cliente);
    }

    /**
     * Actualiza un cliente, lo busca por su CODIGO, no ID
     * @param sn Scanner para insertar el codigo y los nuevos datos
     */
    private static void busquedaCodigo(Scanner sn){
        System.out.println("\n=== Busqueda por codigo ===");
        int codigo = Funciones.dimeEntero("Inserte codigo" ,sn);

        List<Cliente> clientes = ClienteDao.mostrarClientes();

        Cliente cliente = null;

        for (Cliente c : clientes){
            if (c.getCodigo() == codigo) {
                cliente = c;
                break;
            }
        }

        if (cliente != null){
            System.out.println(cliente);

            cliente.setNombre(Funciones.dimeSiONo("¿Quieres cambiarle el nombre?", sn) ?
                Funciones.dimeString("Inserta nombre", sn) : clientes.getFirst().getNombre());
            cliente.setDireccion(Funciones.dimeSiONo("¿Quieres cambiar la direccion?", sn) ?
                    Funciones.dimeString("Inserte nueva direccion", sn) : clientes.getFirst().getDireccion());

            if (Funciones.dimeSiONo("¿Quieres cambiar el codigo?", sn)){
                List<Integer> codigos = new ArrayList<>();
                for (Cliente c : clientes) codigos.add(c.getCodigo());

                int nuevoCodigo;
                do {
                    nuevoCodigo = Funciones.dimeEntero("Inserte codigo", sn);
                } while (codigos.contains(nuevoCodigo));
                cliente.setCodigo(nuevoCodigo);
            }

            ClienteDao.actualizarCliente(cliente);
        } else {
            System.err.println("No hay clientes con ese codigo");
            if (Funciones.dimeSiONo("¿Quieres dar de alta al cliente?", sn)){
                altaCliente(sn);
            }
        }

    }
}
