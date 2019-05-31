import java.sql.*;

public class Conexion {
    String URL = "jdbc:postgresql://localhost:5432/VideoJuego";
    String USER = "vamp";
    String PASS = "Vampire1";

    public Conexion(){   }

    public Connection getConexion(){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
