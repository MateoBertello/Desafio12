package desafiobd12.dao;

import desafiobd12.conexion.ConexionAcademia;
import desafiobd12.modelo.Estudiante;
import java.sql.*;

public class EstudianteDAO {

    public boolean insertar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, apellido) VALUES (?, ?)";
        try (Connection conn = ConexionAcademia.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getApellido());
            
            boolean insertado = pstmt.executeUpdate() > 0;
            if (insertado) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        estudiante.setId(rs.getInt(1));
                    }
                }
            }
            return insertado;
        } catch (SQLException e) {
            System.err.println("Error al insertar estudiante: " + e.getMessage());
            return false;
        }
    }
    
    // (Puedes agregar obtenerPorId, obtenerTodos, etc., si lo deseas)
}