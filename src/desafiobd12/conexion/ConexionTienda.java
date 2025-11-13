package desafiobd12.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Renombrada de ConexionDB a ConexionTienda
public class ConexionTienda {
    
    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456"; 
    
    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
    }
}