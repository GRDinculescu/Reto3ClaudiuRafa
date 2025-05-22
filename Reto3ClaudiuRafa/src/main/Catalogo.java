package main;

import dao.CategoriaDao;
import dao.ProductoDao;
import modelos.Categoria;
import modelos.Producto;
import util.Funciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Giovanni
 * @version 0.1
 * @since 15/05/2025
 */
public class Catalogo {
    /**
     * Muestra el sub-menu de Catalogo
     * @param sn Scanner para pasar a otras funciones o preguntar la opcion
     */
    public static void menu(Scanner sn){
        System.out.println("\n=== Productos ===");
        String menu = """
                
                Elige opcion:
                1. Listar productos por categoría
                2. Buscar productos
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0) return;

            switch (op){
                case 1 -> listarProductosPorCategoria(sn);
                case 2 -> buscarProductos(sn);
                default -> System.err.println("--- Opcion invalida ---");
            }
        }
    }

    /**
     * Muestra las categorias, deja al usuario que elija,
     * y muestra los productos de esa categoria
     * @param sn Scanner para preguntar el id de la categoria
     */
    private static void listarProductosPorCategoria(Scanner sn) {
        System.out.println("\n=== Listar productos por categoria ===");

        List<Categoria> categorias = CategoriaDao.mostrarCategorias(); // Lista de categorias
        List<Integer> ids = new ArrayList<>();
        for (Categoria c : categorias) ids.add(c.getId()); // Guarda los IDs

        System.out.println("Categorias:");
        categorias.forEach(System.out::println);

        int op = Funciones.dimeEntero("Inserte id categoria", sn);
        while (!ids.contains(op)) { // Revisar si esta en la lista
            System.err.println("ID no encontrado");
            op = Funciones.dimeEntero("Inserte id categoria", sn);
        }

        // Muestra los productos de esa categoria
        String filter = "where idCategoria = " + op;
        List<Producto> productos = ProductoDao.mostrarProductos(filter);
        System.out.println("Productos de esa categoria:");
        productos.forEach(System.out::println);
    }

    /**
     * Pregunta caracteristicas de prodcutos, y muestra productos
     * que cumplan esas caracteristicas. Lo hace de forma dinamica,
     * permitiendo valores vacios
     * @param sn Scanner para preguntar las caracteristicas
     */
    private static void buscarProductos(Scanner sn) {
        System.out.println("\n=== Buscar productos ===");
        StringBuilder filter = new StringBuilder();

        String nombre = Funciones.dimeStringVacio("Inserta nombre", sn);
        //nombre.replace("\"", "").replace("'", "");
        String talla = Funciones.dimeStringVacio("Inserta talla", sn);
        //talla.replace("\"", "").replace("'", "");
        String color = Funciones.dimeStringVacio("Inserta color", sn);
        //color.replace("\"", "").replace("'", "");

        // Filtro dinamico
        if (!nombre.isEmpty()) filter.append("nombre like \"%").append(nombre).append("%\"");
        if (!talla.isEmpty()) {
            if (!filter.isEmpty()) filter.append(" and ");
            filter.append("talla like \"%").append(talla).append("%\"");
        }
        if (!color.isEmpty()) {
            if (!filter.isEmpty()) filter.append(" and ");
            filter.append("color like \"%").append(color).append("%\"");
        }

        // Si no esta vacio, se le inserta el where
        if (!filter.isEmpty()) filter.insert(0, "where ");

        // Se obtienen los productos
        List<Producto> productos = ProductoDao.mostrarProductos(filter.toString());

        // Se imprimen
        productos.forEach(System.out::println);
    }
}