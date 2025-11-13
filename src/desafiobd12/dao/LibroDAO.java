package desafiobd12.dao; // Paquete corregido

// Imports corregidos
import desafiobd12.conexion.ConexionBiblioteca; 
import desafiobd12.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, ano_publicacion, isbn, disponible) VALUES (?, ?, ?, ?, ?)";
        // Usamos la conexión correcta
        try (Connection conn = ConexionBiblioteca.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            // ... (El resto del método es idéntico) ...
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setString(4, libro.getIsbn());
            pstmt.setBoolean(5, libro.isDisponible());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }

    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros ORDER BY autor, titulo";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }

    public Libro obtenerPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return crearLibroDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, ano_publicacion=?, isbn=?, disponible=? WHERE id=?";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            // ... (El resto del método es idéntico) ...
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setString(4, libro.getIsbn());
            pstmt.setBoolean(5, libro.isDisponible());
            pstmt.setInt(6, libro.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    // ... (Métodos adicionales como obtenerPorAutor y obtenerDisponibles usan ConexionBiblioteca) ...
    public List<Libro> obtenerPorAutor(String autor) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE autor LIKE ? ORDER BY ano_publicacion";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(crearLibroDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libros por autor: " + e.getMessage());
        }
        return libros;
    }

    public List<Libro> obtenerDisponibles() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE disponible = TRUE ORDER BY titulo";
        try (Connection conn = ConexionBiblioteca.obtenerConexion(); // Conexión corregida
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libros disponibles: " + e.getMessage());
        }
        return libros;
    }


    private Libro crearLibroDesdeResultSet(ResultSet rs) throws SQLException {
        return new Libro(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getInt("ano_publicacion"),
            rs.getString("isbn"),
            rs.getBoolean("disponible")
        );
    }
}