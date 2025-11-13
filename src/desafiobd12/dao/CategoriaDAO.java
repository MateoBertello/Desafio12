package desafiobd12.dao; // Paquete corregido

// Imports corregidos
import desafiobd12.conexion.ConexionTienda;
import desafiobd12.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, categoria.getNombre());
            
            boolean insertado = pstmt.executeUpdate() > 0;
            if (insertado) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setId(rs.getInt(1));
                    }
                }
            }
            return insertado;
        } catch (SQLException e) {
            System.err.println("Error al insertar categoria: " + e.getMessage());
            return false;
        }
    }

    public Categoria obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return crearCategoriaDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar categoria: " + e.getMessage());
        }
        return null;
    }

    public List<Categoria> obtenerTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY nombre";
        try (Connection conn = ConexionTienda.obtenerConexion(); // Conexión corregida
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categorias.add(crearCategoriaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categorias: " + e.getMessage());
        }
        return categorias;
    }
    
    private Categoria crearCategoriaDesdeResultSet(ResultSet rs) throws SQLException {
        return new Categoria(
            rs.getInt("id"),
            rs.getString("nombre")
        );
    }
}