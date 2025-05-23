package main;

import util.Funciones;

import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sn = new Scanner(System.in);
		Random r = new Random();

		String menu = """
				
				Elige opcion:
				1. Mantenimientos
				2. Catalogo de productos
				3. Pedidos
				4. Informes
				0. Salir""";
		int op;

		while (true) {
			op = Funciones.dimeEntero(menu, sn);

			if (op == 0) {
				break;
			}

			switch (op) {
				case 1 -> {
					Mantenimientos.menu(sn);
				}
				case 2 -> {
					Catalogo.menu(sn);
				}
				case 3 -> {
					Pedidos.menu(sn);
				}
				case 4 -> {
					Informes.menu(sn);
				}
				default -> System.err.println("--- Opcion invalida ---");
			}
		}
	}

}
