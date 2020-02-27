import ventanas.VentanaSplash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Entrada {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        VentanaSplash ventanaSplash = new VentanaSplash();
        ventanaSplash.mostrar();

    }

    public static class Conexion {
        Connection conexion = null;

        public Conexion() {
            conexionBD();
        }

        public void conexionBD() {

            try {
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/colegiofx", "colegio", "colegio");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Conexion creada");


        }
    }
}

