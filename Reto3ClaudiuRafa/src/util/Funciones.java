package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Funciones {

	public static boolean esInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean esDoble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String dimeString(String inStart, Scanner scanner) {
		System.out.println(inStart);
		return scanner.nextLine();
	}
	
	public static int dimeEntero(String inStart, Scanner scanner) {
		while (true) {
			System.out.println(inStart);
			String str = scanner.nextLine();
			if(esInt(str)) {
				return Integer.parseInt(str);
			}
			System.out.println("No es un numero");
		}
	}

	public static double dimeDouble(String inStart, Scanner scanner) {
		while (true) {
			System.out.println(inStart);
			String str = scanner.nextLine();
			if(esDoble(str)) {
				return Double.parseDouble(str);
			}
			System.out.println("No es un numero");
		}
	}
	
	public static boolean esPrimo(int num){
        for (int i = 2; i * i <= num; i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }
	
	public static LocalDate dimeFechaStr(String inStart, Scanner scanner) {
		while (true) {
			System.out.println(inStart);
			String str = scanner.nextLine();
			DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			try {
				return LocalDate.parse(str, formato1);
			} catch (Exception e) {
				System.out.println("Error, pon una fecha valida");
			}
		}
	}
	
	public static LocalDate dimeFechaInt(Scanner sc) {
		while (true) {
			int dia = Funciones.dimeEntero("Dime dia", sc);
			int mes = Funciones.dimeEntero("Dime mes", sc);
			int year = Funciones.dimeEntero("Dime año", sc);
			try {
				return LocalDate.of(year, mes, dia);
			} catch (Exception e) {
				System.out.println("Fecha no valida");
			}
		}
	}
	
	public static <T> void muestraLista(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			if(i != list.size()-1) {
				System.out.print(list.get(i)+", ");
			} else {
				System.out.print(list.get(i));
			}
		}
		System.out.println();
	}
	
	public static <T,G> void mostrarMap(Map<T,G> map) {
		for (Map.Entry<T, G> entry : map.entrySet()) {
			System.out.println(entry.getKey()+" - "+entry.getValue());
		}
	}
	
	public static LocalDate fromDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public static LocalTime fromDateToLocalTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}
	public static Date fromLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	public static Date fromStringToDate(String str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}
	public static String fromDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	public static double redondea(double d)
	{
		//redondea d a 2 decimales
		return Math.round(d*100)/(double)100;
	}

}
