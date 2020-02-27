package controladoras;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Persona;
import ventanas.VentanaLogin;

import java.awt.event.KeyAdapter;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladoraVentanaRegistro implements Initializable {

    @FXML
    Button volver, bRegistrar;

    @FXML
    JFXTextField nombre, apellido, correo, confirmarCorreo, contraseña;

    @FXML
    JFXComboBox ciclo;

    @FXML
    JFXCheckBox conocimientosPrevios;

    Persona p;
    ObservableList observableList;
    ObservableList<Persona> observableListT = FXCollections.observableArrayList();
    Connection conexion = null;
    ControladoraMain controladoraMain = new ControladoraMain();
    ResultSet resultSet;
    ArrayList<String> sentencias;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/layout_ventana_main.fxml"));
        controladoraMain = loader.getController();

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/colegiofx", "colegio", "colegio");
            System.out.println("Conexion creada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        rellenarDatos();
        acciones();
    }

    private void rellenarDatos() {
        ciclo.setVisibleRowCount(3);
        ciclo.setPromptText("Introduce ciclo");
        observableList = FXCollections.observableArrayList();

        observableList.add("DAM");
        observableList.add("EDI");
        observableList.add("TAFAD");
        observableList.add("Marketing");
        observableList.add("ASIR");

        ciclo.setItems(observableList);
    }

    public int añadir() throws SQLException {


        int filas = 0;

        String nombreS = nombre.getText();
        String apellidoS = apellido.getText();
        String correoS = correo.getText();
        String contraseniaS = contraseña.getText();
        Boolean conocimientosPreviosB = conocimientosPrevios.isSelected();
        String cicloS = ciclo.getSelectionModel().getSelectedItem().toString();

        String sql = "INSERT INTO persona (Nombre,Apellido,Correo,Contrasenia,Ciclo,conocimientosPrevios) VALUES (?,?,?,?,?,?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, nombreS);
        sentencia.setString(2, apellidoS);
        sentencia.setString(3, correoS);
        sentencia.setString(4, contraseniaS);
        sentencia.setString(5, cicloS);
        sentencia.setBoolean(6, conocimientosPreviosB);

        filas = sentencia.executeUpdate();
        return filas;
    }


    private void acciones() {
        volver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) nombre.getScene().getWindow();
                stage.hide();

                VentanaLogin ventanaLogin = new VentanaLogin();
            }
        });
        bRegistrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!nombre.getText().isEmpty() && !apellido.getText().isEmpty() && !contraseña.getText().isEmpty() && !correo.getText().isEmpty()) {
                    String nombreS, apellidoS, correoS, confirmaCorreoS, contraseniaS, cicloS, conocimientosPreviosS;

                    nombreS = nombre.getText();
                    apellidoS = apellido.getText();
                    correoS = correo.getText();
                    //confirmaCorreoS = confirmarCorreo.getText();
                    contraseniaS = contraseña.getText();
                    cicloS = ciclo.getSelectionModel().getSelectedItem().toString();
                    conocimientosPreviosS = "";

                    if (conocimientosPrevios.isSelected()) {
                        conocimientosPreviosS = "1";
                    } else if (!conocimientosPrevios.isSelected()) {
                        conocimientosPreviosS = "0";
                    }

                    p = new Persona(nombreS, apellidoS, correoS, contraseniaS, cicloS, conocimientosPreviosS);

                    try {
                            if (correo.getText().equals(confirmarCorreo.getText())) {

                                añadir();

                                String sql = String.format("INSERT INTO persona (Nombre, Apellido, Correo) VALUES" +
                                        " ('" + p.getNombre() + "', '" + p.getApellido() + "', '" + p.getCorreo() + "');");
                                //System.out.println(sql);
                                //conexion.createStatement().executeUpdate(sql);
                                //controladoraMain.tabla.setItems();

                                System.out.println("Usuario registrado correctamente");

                                nombre.setText("");
                                apellido.setText("");
                                correo.setText("");
                                confirmarCorreo.setText("");
                                contraseña.setText("");
                            } else {
                                Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                                dialogoWarning.setTitle("Warning");
                                dialogoWarning.setHeaderText("Registro no correcto");
                                dialogoWarning.setContentText("El correo tiene que ser confirmado");
                                dialogoWarning.showAndWait();
                            }




                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else {
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Warning");
                    dialogoWarning.setHeaderText("Registro no correcto");
                    dialogoWarning.setContentText("Faltan datos");
                    dialogoWarning.showAndWait();
                }
            }
        });
    }

    class ManejoPalabras extends KeyAdapter {

    }
}