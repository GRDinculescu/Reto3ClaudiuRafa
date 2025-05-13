package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Pedido;
import util.Conexion;

/**
 * El DAO para modificar y listar pedidos
 */
public class PedidoDao {
	/**
	 * Una lista de los pedidos que existen, sin filtros
	 * @return Una lista de los pedidos que existen, sin filtros
	 */
	public static List<Pedido> mostrarPedidos() {
		 ArrayList<Pedido> listaPedidos;

		listaPedidos = new ArrayList<Pedido>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Pedidos");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaPedidos.add(new Pedido(rs.getInt("idPedido"), ClienteDao.mostrarClientes(rs.getInt("idCliente")), rs.getDouble("precioTotal"), rs.getString("direccionEnvio"), rs.getDate("fecha")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPedidos;
	}
	
	/**
	 * Te devuelve el pedido con dicho ID
	 * @return El pedido que tenga ese ID
	 */
	public static Pedido mostrarPedidos(int id) {
		 Pedido pedido = null;

		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Pedidos WHERE idpedido = ?");
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				pedido = new Pedido(rs.getInt("idPedido"), ClienteDao.mostrarClientes(rs.getInt("idCliente")), rs.getDouble("precioTotal"), rs.getString("direccionEnvio"), rs.getDate("fecha"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedido;
	}
	
	/**
	 * Añade una pedido a la BD
	 * @param Pedido El pedido a añadir
	 */
	public static void insertarPedido(Pedido pedido) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO pedidos (idCliente, precioTotal, direccionEnvio, fecha) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, pedido.getCliente().getId());
			stmt.setDouble(2, pedido.getPrecioTotal());
			stmt.setString(3, pedido.getDireccionEnvio());
			stmt.setDate(4, util.Funciones.convierteFecha(pedido.getFecha()));

			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				pedido.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
