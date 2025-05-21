package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Pedido;
import modelos.PedidoProducto;
import modelos.Producto;
import util.Conexion;
import util.Funciones;

public class PedidoProductoDao {
	/**
	 * Una lista de los pedidos de productos que existen, sin filtros
	 * @return Una lista de los pedidos de productos que existen, sin filtros
	 */
	public static List<PedidoProducto> mostrarPedidoProductos() {
		 ArrayList<PedidoProducto> listaPedidoProductos;

		listaPedidoProductos = new ArrayList<PedidoProducto>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM PedidoProductos");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaPedidoProductos.add(new PedidoProducto(rs.getInt("idPedidoProducto"), PedidoDao.mostrarPedidos(rs.getInt("idpedido")), ProductoDao.mostrarProductos(rs.getInt("idProducto")), rs.getInt("unidades"), rs.getDouble("precio")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPedidoProductos;
	}
	
	/**
	 * Te devuelve el pedido de productos con dicho ID
	 * @param id El id que se buscara
	 * @return El pedido de productos que tenga ese ID
	 */
	public static PedidoProducto mostrarPedidoProductos(int id) {
		 PedidoProducto pedidoProducto = null;

		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pedidoProducto WHERE idPedidoProducto = ?");
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				pedidoProducto = new PedidoProducto(rs.getInt("idPedidoProducto"), PedidoDao.mostrarPedidos(rs.getInt("idpedido")), ProductoDao.mostrarProductos(rs.getInt("idProducto")), rs.getInt("unidades"), rs.getDouble("precio"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedidoProducto;
	}
	
	/**
	 * Añade un pedido de productos a la BD
	 * @param pedidoProducto El pedido de productos a añadir
	 */
	public static void insertarPedidoProducto(PedidoProducto pedidoProducto) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO pedidoProducto (idpedido, idproducto, unidades, precio) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, pedidoProducto.getPedido().getId());
			stmt.setInt(2, pedidoProducto.getProducto().getId());
			stmt.setInt(3, pedidoProducto.getUnidades());
			stmt.setDouble(4, pedidoProducto.getPrecio());

			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				pedidoProducto.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Te devuelve los pedidos con los productos con el filtro añadido
	 * @param filter El filtro que se añadira a la sentencia SQL (Despues de "SELECT * FROM PedidoProducto ")
	 * @return Los pedidos que coincidan con ese filtro
	 */
	public static List<PedidoProducto> mostrarPedidoProductos(String filter) {
		ArrayList<PedidoProducto> listaPedidoProductos;

		listaPedidoProductos = new ArrayList<PedidoProducto>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pedidoProducto "+filter);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaPedidoProductos.add(new PedidoProducto(rs.getInt("idPedidoProducto"), PedidoDao.mostrarPedidos(rs.getInt("idpedido")), ProductoDao.mostrarProductos(rs.getInt("idProducto")), rs.getInt("unidades"), rs.getDouble("precio")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPedidoProductos;
	}
	
	/**
	 * Te devuelve los pedidos con los productos que pertenezcan al cliente con ese codigo
	 * @param codigoCliente el codigo del cliente que bucar
	 * @return Los pedidos que pertenezcan al cliente con ese codigo
	 */
	public static List<PedidoProducto> mostrarPedidoProductosPorCodigoCliente(int codigoCliente) {
		ArrayList<PedidoProducto> listaPedidoProductos;

		listaPedidoProductos = new ArrayList<PedidoProducto>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pedidoProducto pp"
					+ " INNER JOIN pedidos p ON pp.idPedido = p.idPedido"
					+ " INNER JOIN clientes c ON p.idCliente = c.idCliente"
					+ " WHERE c.codigo = ?");
			
			stmt.setInt(1, codigoCliente);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaPedidoProductos.add(new PedidoProducto(rs.getInt("idPedidoProducto"), PedidoDao.mostrarPedidos(rs.getInt("idpedido")), ProductoDao.mostrarProductos(rs.getInt("idProducto")), rs.getInt("unidades"), rs.getDouble("precio")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPedidoProductos;
	}

	public static void mostrarPedidos(List<PedidoProducto> pedidoProductos) {
		if (!pedidoProductos.isEmpty()){
			Pedido lastPedido = new Pedido();
			for (PedidoProducto pp : pedidoProductos){
				Pedido pedido = pp.getPedido();
				Producto producto = pp.getProducto();

				if (pedido.getId() != lastPedido.getId()){
					System.out.printf("%n[%s] Total: %.2f$ | Direccion de envio: %s%n",
							Funciones.convierte_Date_a_String(pedido.getFecha()), pedido.getPrecioTotal(), pedido.getDireccionEnvio());
				}

				System.out.printf("    Categoria: %s | Nombre: %s | Unidades: %d%n",
						producto.getCategoria().getNombre(), producto.getNombre(), pp.getUnidades());

				lastPedido = pedido;
			}
			System.out.println(); // Espacio para separar
		} else {
			System.out.println("El cliente no tiene pedidos\n");
		}
	}
	
}
