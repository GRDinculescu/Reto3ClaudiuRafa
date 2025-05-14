package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import modelos.PedidoProducto;
import util.Conexion;

/**
 * El DAO para modificar y listar clientes
 */
public class ClienteDao {
	/**
	 * Una lista de las clientes que existen, sin filtros
	 * @return Una lista de las clientes que existen, sin filtros
	 */
	public static List<Cliente> mostrarClientes() {
		 ArrayList<Cliente> listaClientes;

		listaClientes = new ArrayList<Cliente>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Clientes");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaClientes.add(new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("direccion"), rs.getInt("codigo")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaClientes;
	}
	
	/**
	 * Te devuelve el cliente con dicho ID
	 * @param id El id que se buscara
	 * @return El cliente que tenga ese ID
	 */
	public static Cliente mostrarClientes(int id) {
		 Cliente cliente = null;

		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Clientes WHERE idCliente = ?");
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				cliente = new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("direccion"), rs.getInt("codigo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	/**
	 * Añade una cliente a la BD
	 * @param Cliente El cliente a añadir
	 */
	public static void insertarCliente(Cliente Cliente) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO Clientes (nombre, direccion, codigo) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, Cliente.getNombre());
			stmt.setString(2, Cliente.getDireccion());
			stmt.setInt(3, Cliente.getCodigo());

			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				Cliente.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Te devuelve los clientes con el filtro añadido
	 * @param filter El filtro que se añadira a la sentencia SQL (Despues de "SELECT * FROM Pedidos ")
	 * @return Los clientes que coincidan con ese filtro
	 */
	public static List<Cliente> mostrarClientes(String filter) {
		ArrayList<Cliente> listaClientes;

		listaClientes = new ArrayList<Cliente>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Clientes "+filter);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaClientes.add(new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("direccion"), rs.getInt("codigo")));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaClientes;
	}
	
}
