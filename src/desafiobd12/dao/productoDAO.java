package desafiobd12.dao; // Paquete corregido

// Imports corregidos
import desafiobd12.conexion.ConexionTienda;
import desafiobd12.modelo.Categoria;
import desafiobd12.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productoDAO { // Nota: Por convención de Java, esta clase debería llamarse "ProductoDAO"
    
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            // ... (El resto del método es idéntico) ...
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.setInt(4, producto.getCategoria().getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, precio=?, stock=?, categoria_id=? WHERE id=?";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            // ... (El resto del método es idéntico) ...
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

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "INNER JOIN categorias c ON p.categoria_id = c.id " +
                     "ORDER BY p.nombre";
        
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
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

    public Producto obtenerPorId(int id) {
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                     "FROM productos p " +
                     "INNER JOIN categorias c ON p.categoria_id = c.id " +
                     "WHERE p.id = ?";
        
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
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
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    private Producto crearProductoDesdeResultSet(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(
            rs.getInt("categoria_id"),
            rs.getString("categoria_nombre")
        );
        
        return new Producto(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getDouble("precio"),
            rs.getInt("stock"),
            categoria
        );
    }
}