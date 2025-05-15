package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Categoria;
import modelos.Cliente;
import modelos.Pedido;
import modelos.Producto;
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
	 * @param id El id que se buscara
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
	 * @param pedido El pedido a añadir
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
	
	/**
	 * Te devuelve los pedidos con el filtro añadido
	 * @param filter El filtro que se añadira a la sentencia SQL (Despues de "SELECT * FROM Pedidos ")
	 * @return Los pedidos que coincidan con ese filtro
	 */
	public static List<Pedido> mostrarPedidos(String filter) {
		ArrayList<Pedido> listaPedidos;

		listaPedidos = new ArrayList<Pedido>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Pedidos "+filter);

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
	 * Muestra los pedidos de un cliente buscandolo por su codigo
	 * @param codigo Codigo del cliente. NO el ID
	 * @return Devuelve lista de pedidos. Si no tiene, estara vacia.
	 */
	public static List<Pedido> mostrarPedidosPorCodigoCliente(int codigo){
		List<Pedido> pedidos = new ArrayList<>();

		try (Connection con = Conexion.abreconexion()) {
			String query = "SELECT p.idPedido, p.idCliente, p.precioTotal, p.direccionEnvio, p.fecha FROM pedidos p\n" +
					"inner join clientes c on p.idCliente = c.idCliente\n" +
					"where c.codigo = ?;";
			PreparedStatement stmt = con.prepareStatement(query);

			stmt.setInt(1, codigo);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				Cliente c = new Cliente();
				c.setId(rs.getInt("p.idCliente"));

				Pedido p = new Pedido(
						rs.getInt("idPedido"),
						c, // Del cliente solo queremos su id
						rs.getDouble("precioTotal"),
						rs.getString("direccionEnvio"),
						rs.getDate("fecha")
				);
				pedidos.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pedidos;
	}
}
