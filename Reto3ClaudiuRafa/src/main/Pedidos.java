package main;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDao;
import dao.PedidoDao;
import dao.PedidoProductoDao;
import dao.ProductoDao;
import modelos.Cliente;
import modelos.Pedido;
import modelos.PedidoProducto;
import modelos.Producto;
import util.Funciones;

public class Pedidos {
    public static void menu(Scanner sn){
        String menu = """
                Elige opcion:
                1. Crear pedido
                2. Ver pedidos
                0. Salir""";
        int op;

        while (true){
            op = Funciones.dimeEntero(menu, sn);

            if (op == 0){
                return;
            }

            switch (op){
                case 1 -> {
                    System.out.println("=== Creacion de pedido ===");
                    crearPedido(sn);
                }
                case 2 -> {
                    System.out.println("=== Pedidos del mes ===");
                    mostrarPedidos(sn);
                }
                default -> System.out.println("--- Opcion invalida ---");
            }
        }
    }

    private static void crearPedido(Scanner sn){
    	
    	// Que eliga su codigo de cliente
    	int code = -1;
    	List<Cliente> clientesTemp;
    	do {
    		code = util.Funciones.dimeEntero("\nIntroduce tu codigo de cliente ([-1] para salir): ", sn);
    		if(code == -1) {return;}
    		clientesTemp = ClienteDao.mostrarClientes("WHERE codigo = "+code);
		} while (clientesTemp.isEmpty());
    	
    	// Cliente elegido
    	Cliente cliente = clientesTemp.get(0);
    	System.out.println("Creando pedido para "+cliente.getNombre()+"...");
    	
    	
    	List<Producto> productos = new ArrayList<Producto>();
    	List<Producto> productosDB = ProductoDao.mostrarProductos();
    	List<Pedido> pedidos = new ArrayList<Pedido>();
    	List<PedidoProducto> pedidoProductos = new ArrayList<PedidoProducto>();

    	// Ir añadiendo pedidos
    	while (true) {
    		// Preguntar pedido
    		productosDB.forEach(System.out::println);
    		System.out.println("Introduce el nombre del producto que quieres añadir a la cesta (No introduzcas nada para salir): ");
    		String nombre = sn.nextLine();
    		// Salir si se pide
    		if (nombre.isEmpty()) break;
    		// Comprobar si existe pedido
    		Producto producto = null;
    		for (Producto productoTemp : productosDB) {
				if(productoTemp.getNombre().equals(nombre)) producto = productoTemp;
			}
    		if (producto == null) {
    			System.out.println("Pedido no encontrado");
    		} else {
    			// Comprar pedido
    			int unidades = util.Funciones.dimeEntero("¿Cuantas unidades de ese producto quieres comprar? ", sn);
    			
    			// Añadir el producto a la cesta
    			if(producto.getStock() < unidades) { // Si no hay stock suficiente
    				unidades = producto.getStock();
    				producto.setStock(0);
    				if (!productos.contains(producto)) productos.add(producto);
    				System.out.println("Se han añadido a la cesta "+unidades+" unidades de "+producto.getNombre()+" debido a que se ha agotado el stock");
    			
    			} else { // Si hay stock suficiente
    				producto.setStock(producto.getStock()-unidades);
    				if (!productos.contains(producto)) productos.add(producto);
    				System.out.println("Se han añadido a la cesta "+unidades+" unidades de "+producto.getNombre());
    			}
    			
    			// Crear y añadir pedidos a la cesta
    			Pedido pedido = new Pedido(cliente, producto.getPrecio()*unidades, null, Date.from(Instant.now()));
    			PedidoProducto pedidoProducto = new PedidoProducto(pedido, producto, unidades, producto.getPrecio());
    			pedidos.add(pedido);
    			pedidoProductos.add(pedidoProducto);
    		}
    	}
    	
    	if(!pedidos.isEmpty()) {
        	// Se seleccion la direccion de envio
        	String dirEnvio = cliente.getDireccion();
        	
        	boolean decidido = false;
        	while(!decidido) {
        		decidido = util.Funciones.dimeSiONo("¿La direccion de envio es '"+dirEnvio+"'?", sn);
            	if (!decidido) {
        			System.out.println("Introduce una nueva direccion de envio: ");
        			dirEnvio = sn.nextLine();
        		}
        	}
        	
        	// Guradar pedido
        	
        	double precioTotal = 0;
        	productos.forEach(ProductoDao::actualizarProducto);

        	for (Pedido pedido : pedidos) {
				precioTotal+=pedido.getPrecioTotal();
				pedido.setDireccionEnvio(dirEnvio);
				PedidoDao.insertarPedido(pedido);
			}
        	
        	pedidoProductos.forEach(PedidoProductoDao::insertarPedidoProducto);

        	System.out.println("Pedido creado, el precio total ha sido de "+precioTotal+"€");
        	
    	} else {
    		System.out.println("¡Esperamos poder ayudarle a la proxima!");
    	}

    }

    private static void mostrarPedidos(Scanner sn){
    	PedidoProductoDao.mostrarPedidoProductos(" INNER JOIN pedidos ON pedidoProducto.idPedido = pedidos.idPedido WHERE MONTH(pedidos.fecha) = MONTH(NOW()) ORDER BY Fecha DESC").forEach(t -> {
    		System.out.println("\nFecha: "+t.getPedido().getFecha()+
    				"\nDireccion de envio: "+t.getPedido().getDireccionEnvio()+
    				"\nPrecio total: "+t.getPedido().getPrecioTotal()+
    				"\nUnidades compradas: "+t.getUnidades()+
    				"\nNombre del cliente: "+t.getPedido().getCliente().getNombre()+
    				"\nProducto: "+t.getProducto().getNombre() +
    				"\nCategoria del producto: "+t.getProducto().getCategoria());
    	});
    }
}
