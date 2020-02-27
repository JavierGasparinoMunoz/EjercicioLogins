package controladoras;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.Persona;
import ventanas.VentanaLogin;
import ventanas.VentanaMain;
import ventanas.VentanaRegistro;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladoraMain implements Initializable {

    @FXML
    Button volver, borrar, añadir;

    @FXML
    JFXTextField nombre;

    @FXML
    TableView<Persona> tabla;

    @FXML
    TableColumn<Persona, String> nombreC;

    @FXML
    TableColumn<Persona, String> apellido;

    @FXML
    TableColumn<Persona, String> correo;

    ObservableList<Persona> observableListTabla = FXCollections.observableArrayList();
    Driver_MySQL driver_mySQL = new Driver_MySQL();
    Persona p;
    FilteredList<Persona> datosFiltrados = new FilteredList<>(observableListTabla, b -> true);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        rellenarTabla();
        acciones();
        filtrar();
    }

    private void filtrar() {
        nombre.textProperty().addListener((observable, oldValue, newValue) -> {
            datosFiltrados.setPredicate(persona -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (persona.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (persona.getApellido().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if ((persona.getCorreo().indexOf(lowerCaseFilter) != -1)) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<Persona> sortedData = new SortedList<>(datosFiltrados);

        sortedData.comparatorProperty().bind(tabla.comparatorProperty());

        tabla.setItems(sortedData);
    }


    private void instancias() {
        nombre.setPromptText("nombre a filtrar");
    }

    private void rellenarTabla() {


        try {
            Connection con = driver_mySQL.conectar();
            ResultSet resultSet = con.createStatement().executeQuery("SELECT nombre,apellido,correo from persona");

            while (resultSet.next()) {
                observableListTabla.add(new Persona(resultSet.getString("Nombre"), resultSet.getString("Apellido"), resultSet.getString("Correo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nombreC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        correo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        tabla.setItems(observableListTabla);
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

        borrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    p = tabla.getSelectionModel().getSelectedItem();
                    driver_mySQL.eliminar(p.getCorreo());
                    observableListTabla.remove(p);
                    tabla.refresh();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        añadir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Window window = borrar.getScene().getWindow();
                window.hide();

                VentanaRegistro ventanaRegistro = new VentanaRegistro();
            }
        });
    }
}