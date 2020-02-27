package controladoras;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.Persona;
import ventanas.VentanaMain;
import ventanas.VentanaRegistro;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControladoraVentanaLogin implements Initializable {

    @FXML
    Button iniciarSesion,noRecuerdo,registrarme;

    @FXML
    JFXCheckBox recordar;

    @FXML
    JFXTextField usuario,contrasenia;

    Persona p;
    Connection conexion = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Driver_MySQL driver_mySQL = new Driver_MySQL();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();

    }

    private boolean login() throws SQLException {
        String usuarioS = usuario.getText();
        String contraseniaS = contrasenia.getText();

        driver_mySQL.conectar();

        String sql = "SELECT * FROM persona Where Nombre = ? and Contrasenia = ?";

        try {
            preparedStatement = driver_mySQL.conectar().prepareStatement(sql);
            preparedStatement.setString(1,usuarioS);
            preparedStatement.setString(2,contraseniaS);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Introduce usuario/contrasenia correcto");
                return false;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Login satisfactorio");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void acciones() {
        iniciarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!usuario.getText().isEmpty() && !contrasenia.getText().isEmpty()){
                    String usuarioPersona = usuario.getText();
                    String contraseniaPersona = contrasenia.getText();

                    p = new Persona(usuarioPersona, contraseniaPersona);

                    try {
                        if(login()){
                            Window window = usuario.getScene().getWindow();
                            window.hide();

                            VentanaMain ventanaMain = new VentanaMain();

                        }else{
                            Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                            dialogoWarning.setTitle("Warning");
                            dialogoWarning.setHeaderText("Login incorrecto");
                            dialogoWarning.setContentText("Nombre o contraseña incorrectos");
                            dialogoWarning.showAndWait();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Warning");
                    dialogoWarning.setHeaderText("Login incorrecto");
                    dialogoWarning.setContentText("Faltan datos");
                    dialogoWarning.showAndWait();
                }

            }
        });

        registrarme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VentanaRegistro ventanaRegistro = new VentanaRegistro();
                Stage stage = (Stage) iniciarSesion.getScene().getWindow();
                stage.hide();
            }
        });
    }

    private void instancias() {
        usuario.setPromptText("Introduce usuario");
        contrasenia.setPromptText("Introduce contraseña");
    }
}