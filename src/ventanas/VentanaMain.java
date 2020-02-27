package ventanas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class VentanaMain extends Stage {

    public VentanaMain() {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("../layouts/layout_ventana_main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root,800,600);
        this.setScene(scene);
        this.setTitle("Main");
        this.initStyle(StageStyle.TRANSPARENT);
        this.show();
    }

}
