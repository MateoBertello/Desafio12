package desafiobd12.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionAcademia {
    
    private static final String URL = "jdbc:mysql://localhost:3306/academia";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456"; // Tu contraseña
    
    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
    }
}