package ventanas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class VentanaLogin extends Stage {

    public VentanaLogin(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../layouts/layout_scene_login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,800,600);
        this.setScene(scene);
        this.setTitle("Login");
        this.initStyle(StageStyle.TRANSPARENT);
        this.show();
    }


}
