package desafiobd12.dao;

import desafiobd12.conexion.ConexionAcademia;
import desafiobd12.modelo.Calificacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionDAO {

    /**
     * Requerimiento 1: Registrar calificaciones
     */
    public boolean registrar(Calificacion calificacion) {
        String sql = "INSERT INTO calificaciones (estudiante_id, materia, nota, fecha) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionAcademia.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, calificacion.getEstudianteId());
            pstmt.setString(2, calificacion.getMateria());
            pstmt.setDouble(3, calificacion.getNota());
            pstmt.setDate(4, calificacion.getFecha());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar calificacion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Requerimiento 2: Listar el historial completo de un estudiante
     */
    public List<Calificacion> obtenerHistorial(int estudianteId) {
        List<Calificacion> historial = new ArrayList<>();
        String sql = "SELECT * FROM calificaciones WHERE estudiante_id = ? ORDER BY fecha DESC, materia";
        
        try (Connection conn = ConexionAcademia.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, estudianteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    historial.add(crearCalificacionDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
        }
        return historial;
    }

    /**
     * Requerimiento 3: Calcular el promedio de un estudiante
     */
    public double calcularPromedio(int estudianteId) {
        String sql = "SELECT AVG(nota) as promedio FROM calificaciones WHERE estudiante_id = ?";
        double promedio = 0.0;
        
        try (Connection conn = ConexionAcademia.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, estudianteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    promedio = rs.getDouble("promedio");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular promedio: " + e.getMessage());
        }
        return promedio;
    }

    private Calificacion crearCalificacionDesdeResultSet(ResultSet rs) throws SQLException {
        return new Calificacion(
            rs.getInt("id"),
            rs.getInt("estudiante_id"),
            rs.getString("materia"),
            rs.getDouble("nota"),
            rs.getDate("fecha")
        );
    }
}