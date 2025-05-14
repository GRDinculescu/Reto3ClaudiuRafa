package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Categoria;
import modelos.Producto;
import util.Conexion;

/**
 * El DAO para modificar y listar categorias
 */
public class CategoriaDao {
	
	/**
	 * Una lista de las categorias que existen, sin filtros
	 * @return Una lista de las categorias que existen, sin filtros
	 */
	public static List<Categoria> mostrarCategorias() {
		 ArrayList<Categoria> listaCategorias;

		listaCategorias = new ArrayList<Categoria>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Categorias");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaCategorias.add(new Categoria(rs.getInt("idCategoria"), rs.getString("nombre")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCategorias;
	}
	
	/**
	 * Te devuelve la categoria con dicho ID
	 * @param id El id que se buscara
	 * @return La categoria que tenga ese ID
	 */
	public static Categoria mostrarCategorias(int id) {
		 Categoria categoria;

		 categoria = new Categoria();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Categorias WHERE idCategoria = ?");
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				categoria = new Categoria(rs.getInt("idCategoria"), rs.getString("nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoria;
	}
	
	/**
	 * Añade una categoria a la BD
	 * @param Categoria La categoria a añadir
	 */
	public static void insertarCategoria(Categoria Categoria) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO Categorias (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, Categoria.getNombre());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				Categoria.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Te devuelve las categorias con el filtro añadido
	 * @param filter El filtro que se añadira a la sentencia SQL (Despues de "SELECT * FROM Categorias ")
	 * @return Las categorias que coincidan con ese filtro
	 */
	public static List<Categoria> mostrarCategorias(String filter) {
		ArrayList<Categoria> listaCategorias;

		listaCategorias = new ArrayList<Categoria>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Categorias "+filter);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaCategorias.add(new Categoria(rs.getInt("idCategoria"), rs.getString("nombre")));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCategorias;
	}
	

}
