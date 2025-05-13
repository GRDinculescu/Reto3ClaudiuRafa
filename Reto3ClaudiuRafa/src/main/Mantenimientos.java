package main;

import util.Funciones;

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

    }

    private static void gestionProductos(Scanner sn){

    }

    private static void gestionClientes(Scanner sn){

    }
}
