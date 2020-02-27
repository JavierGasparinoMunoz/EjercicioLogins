package controladoras;

import utils.Persona;

import java.sql.*;
import java.util.ArrayList;

public class Driver_MySQL {


    ControladoraVentanaRegistro controladoraVentanaRegistro;

    private Connection conexion;
    private Statement st;

    public Driver_MySQL() {
    }

    public  Connection conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/colegiofx", "colegio", "colegio");
            System.out.println("Conexion creada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public ArrayList listar(String sql) {
        ArrayList<Persona> resultado = new ArrayList();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Persona p = new Persona(rs.getString("contrasenia"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("correo"),
                        rs.getString("ciclo"), rs.getString("conocimientosPrevios"));

                resultado.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error en la base de datos" + e.getMessage());
        }
        return resultado;
    }

    public int eliminar(String correo) throws SQLException {
        int filas;
        String sql = "DELETE FROM persona WHERE correo = ? ";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, correo);
        filas = sentencia.executeUpdate();
        return filas;
    }

}