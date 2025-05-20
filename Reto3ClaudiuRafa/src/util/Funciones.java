package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Funciones {

	// String[] dias =
	// {"","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
	// String[] meses =
	// {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio", "Agosto",
	// "Septiembre","Octubre","Noviembre","Diciembre"};

	public static int dimeEntero(String texto, Scanner sc) {
		boolean bien = false;
		do {
			try {
				System.out.println(texto);
				String s = sc.nextLine();
				return Integer.parseInt(s);
			} catch (Exception e) {
				System.out.println("Formato incorrecto");
			}
		} while (!bien);
		return 0;
	}

	public static String dimeString(String texto, Scanner sc) {
		String s = "";
		do {
			System.out.println(texto);
			s = sc.nextLine();
		} while (s.equals(""));
		return s;
	}

	public static double dimeDouble(String texto, Scanner sc) {
		boolean bien = false;
		do {
			try {
				System.out.println(texto);
				String s = sc.nextLine();
				return Double.parseDouble(s);
			} catch (Exception e) {
				System.out.println("Formato incorrecto");
			}
		} while (!bien);
		return 0;
	}
	
	public static LocalDate dimeFecha(String texto, Scanner sc) {
		boolean bien = false;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do {
			try {
				System.out.println(texto + " (dd/mm/yyyy)");
				String s = sc.nextLine();
				return LocalDate.parse(s, dtf);
			} catch (Exception e) {
				System.out.println("Formato incorrecto");
			}
		} while (!bien);
		return null;
	}

	public static boolean isInt(String texto) {
		try {
			Integer.parseInt(texto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String texto) {
		try {
			Double.parseDouble(texto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void escribeArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(i==0?array[i]:", " + array[i]);
		}
		System.out.println();
	}

	public static void escribeArrayS(String[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(i==0?array[i]:", " + array[i]);
		}
		System.out.println();
	}

		public static double redondea(double n) {
		return Math.round(n * 100) / 100.00;
	}

	public static void muestraListaInt(List<Integer> lista) {
		for (int i = 0; i < lista.size(); i++) {
			System.out.print(i==0?lista.get(i):", " + lista.get(i));
		}
		System.out.println();
	}

	public static void muestraListaStr(List<String> lista) {
		for (int i = 0; i < lista.size(); i++) {
			System.out.print(i==0?lista.get(i):", " + lista.get(i));
		}
		System.out.println();
	}
	public static Date convierte_String_a_Date(String fString)
	{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			return sdf.parse(fString);
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	public static String convierte_Date_a_String(Date fDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
		return sdf.format(fDate);
	}
	
	public static LocalDate convierte_Date_a_LocalDate(Date fDate)
	{
		return fDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date convierte_LocalDate_a_Date(LocalDate fLocalDate)
	{
		return Date.from(fLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date fechaNac(Random r)
	{
		String fecha = r.nextInt(1,29)+ "/" + r.nextInt(1,13) + "/" + r.nextInt(1980,2020);
		return Funciones.convierte_String_a_Date(fecha);
	}
	
	public static Date dimeFecha2(String texto, Scanner sc)
	{
		do {
			try {
				System.out.println(texto + " (dd/mm/yyyy)");
				String s = sc.nextLine();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				return sdf.parse(s);
			} catch (Exception e) {
				System.out.println("Formato incorrecto");
			}
		} while (true);
	}
	
	public static java.sql.Date convierteFecha(Date fecha)
	{
		return new java.sql.Date(fecha.getTime());
	}
	
	public static boolean dimeSiONo(String texto, Scanner sc) {
		do {
			try {
				System.out.println(texto+" (s/n)");
				String s = sc.nextLine().toLowerCase();
				if(s.length() != 1) throw new Exception();
				
				if(s.charAt(0) == 's') return true;
				if(s.charAt(0) == 'n') return false;
				
				throw new Exception();
			} catch (Exception e) {
				System.out.println("Formato incorrecto");
			}
		} while (true);
	}

	/**
	 * Pide un string, y admite vacios
	 * @param sn Scanner para el string
	 * @return Devuelve el string que se inserto por consola
	 */
	public static String dimeStringVacio(String mensaje, Scanner sn){
		System.out.println(mensaje);
		return sn.nextLine();
	}
}
