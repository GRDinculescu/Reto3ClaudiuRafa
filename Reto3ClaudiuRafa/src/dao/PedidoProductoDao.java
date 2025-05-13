package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.PedidoProducto;
import util.Conexion;

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
	 * @param Pedido El pedido de productos a añadir
	 */
	public static void insertarPedidoProducto(PedidoProducto pedidoProducto) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO pedidoProducto (idpedido, idproducto, unidades, precio) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, pedidoProducto.getPedido().getId());
			stmt.setInt(2, pedidoProducto.getProducto().getId());
			stmt.setInt(3, pedidoProducto.getUnidades());
			stmt.setDouble(3, pedidoProducto.getPrecio());

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
}
