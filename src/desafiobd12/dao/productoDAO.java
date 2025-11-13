package tienda.dao;

import tienda.conexion.ConexionDB;
import tienda.modelo.Categoria;
import tienda.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Este DAO es como EstudianteDAO. Usa JOINs.
public class ProductoDAO {
    
    /**
     * Inserta un nuevo producto
     */
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.setInt(4, producto.getCategoria().getId()); // ⭐ Guarda solo el ID
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un producto
     */
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, precio=?, stock=?, categoria_id=? WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.setInt(4, producto.getCategoria().getId());
            pstmt.setInt(5, producto.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los productos CON su categoría (usando JOIN)
     * Este método cumple el requisito del ejercicio.
     */
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        
        // ⭐ JOIN para traer datos de la categoría en una sola consulta
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "INNER JOIN categorias c ON p.categoria_id = c.id " +
                     "ORDER BY p.nombre";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(crearProductoDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Busca un producto por ID (con su categoría)
     */
    public Producto obtenerPorId(int id) {
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "INNER JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE p.id = ?";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return crearProductoDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Elimina un producto
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método auxiliar para crear un Producto (con su Categoria) desde un ResultSet
     */
    private Producto crearProductoDesdeResultSet(ResultSet rs) throws SQLException {
        // 1. Crear el objeto Categoria (solo con los datos que trajimos)
        Categoria categoria = new Categoria(
            rs.getInt("categoria_id"),
            rs.getString("categoria_nombre") // Usamos el alias del JOIN
        );
        
        // 2. Crear el objeto Producto
        return new Producto(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getDouble("precio"),
            rs.getInt("stock"),
            categoria // ⭐ Pasamos el objeto Categoria completo
        );
    }
}