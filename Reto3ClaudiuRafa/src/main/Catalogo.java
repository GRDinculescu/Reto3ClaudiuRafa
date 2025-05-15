package main;
/*
2. Catálogo de productos: se mostrará el siguiente submenú
2.1. Listar productos por categoría: mostraremos las categorías por consola,
    mostrando id y nombre, y le pediremos al usuario que elija una.
    Mostraremos los productos de esa categoría.
2.2. Buscar productos: pediremos por consola un nombre, una talla y un color.
    El usuario puede no introducir nada en alguna de esas preguntas (pulsa intro sin escribir nada).
    Buscaremos los productos que cumplan el filtro introducido y los mostraremos por consola.
    Ejemplo: si introduce sólo nombre, buscaremos los que tengan ese nombre contenido, no tiene que ser igual
    (usamos % en el valor del argumento que pasamos, no en el ?). Si introducen sólo en talla y color,
    los que tengan esa talla y ese color.
*/

import util.Funciones;

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
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    private static void listarProductosPorCategoria(Scanner sn) {

    }

    private static void buscarProductos(Scanner sn) {

    }
}
