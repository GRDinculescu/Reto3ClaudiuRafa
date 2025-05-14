package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import modelos.Producto;
import util.Conexion;

public class ProductoDao {
	/**
	 * Una lista de los productos que existen, sin filtros
	 * @return Una lista de los productos que existen, sin filtros
	 */
	public static List<Producto> mostrarProductos() {
		 ArrayList<Producto> listaProductos;

		listaProductos = new ArrayList<Producto>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Productos");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaProductos.add(new Producto(rs.getInt("idProducto"), CategoriaDao.mostrarCategorias(rs.getInt("idCategoria")), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("descripcion"), rs.getString("color"), rs.getString("talla"), rs.getInt("stok")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProductos;
	}
	
	/**
	 * Te devuelve el producto con dicho ID
	 * @param id El id que se buscara
	 * @return El producto que tenga ese ID
	 */
	public static Producto mostrarProductos(int id) {
		 Producto producto = null;

		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM productos WHERE idproducto = ?");
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				producto = new Producto(rs.getInt("idProducto"), CategoriaDao.mostrarCategorias(rs.getInt("idCategoria")), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("descripcion"), rs.getString("color"), rs.getString("talla"), rs.getInt("stok"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return producto;
	}
	
	/**
	 * Añade una producto a la BD
	 * @param producto El producto a añadir
	 */
	public static void insertarProducto(Producto producto) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("INSERT INTO productos (idcategoria, nombre, precio, descripcion, color, talla, stock) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, producto.getCategoria().getId());
			stmt.setString(2, producto.getNombre());
			stmt.setDouble(3, producto.getPrecio());
			stmt.setString(4, producto.getDescripcion());
			stmt.setString(5, producto.getColor());
			stmt.setString(6, producto.getTalla());
			stmt.setInt(7, producto.getStock());

			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				producto.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza una producto de la BD
	 * @param producto El producto a añadir
	 */
	public static void actualizarProducto(Producto producto) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("UPDATE productos SET (idcategoria = ?, nombre = ?, precio = ?, descripcion = ?, color = ?, talla = ?, stock = ?) WHERE idCategoria = ?");
			
			stmt.setInt(1, producto.getCategoria().getId());
			stmt.setString(2, producto.getNombre());
			stmt.setDouble(3, producto.getPrecio());
			stmt.setString(4, producto.getDescripcion());
			stmt.setString(5, producto.getColor());
			stmt.setString(6, producto.getTalla());
			stmt.setInt(7, producto.getStock());
			stmt.setInt(8, producto.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza una producto de la BD con un filtro
	 * !ATENCION: No se aplica el filtro del ID, a si que hay que tener cuidado con eso
	 * @param producto El producto a añadir
	 * @param filter El filtro a añadir
	 */
	public static void actualizarProducto(Producto producto, String filter) {
		
		try (Connection con = Conexion.abreconexion()){
		
			PreparedStatement stmt = con.prepareStatement("UPDATE productos SET (idcategoria = ?, nombre = ?, precio = ?, descripcion = ?, color = ?, talla = ?, stock = ?) "+filter);
			
			stmt.setInt(1, producto.getCategoria().getId());
			stmt.setString(2, producto.getNombre());
			stmt.setDouble(3, producto.getPrecio());
			stmt.setString(4, producto.getDescripcion());
			stmt.setString(5, producto.getColor());
			stmt.setString(6, producto.getTalla());
			stmt.setInt(7, producto.getStock());
			stmt.setInt(8, producto.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Te devuelve los productos con el filtro añadido
	 * @param filter El filtro que se añadira a la sentencia SQL (Despues de "SELECT * FROM Productos ")
	 * @return Los productos que coincidan con ese filtro
	 */
	public static List<Producto> mostrarProductos(String filter) {
		ArrayList<Producto> listaProductos;

		listaProductos = new ArrayList<Producto>();
		try (Connection con = Conexion.abreconexion()){
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Productos "+filter);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaProductos.add(new Producto(rs.getInt("idProducto"), CategoriaDao.mostrarCategorias(rs.getInt("idCategoria")), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("descripcion"), rs.getString("color"), rs.getString("talla"), rs.getInt("stok")));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProductos;
	}
	
}
