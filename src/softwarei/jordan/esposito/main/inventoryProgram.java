package softwarei.jordan.esposito.main;

import softwarei.jordan.esposito.controllers.main_inventoryProgram;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class inventoryProgram extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        main_inventoryProgram starter = new main_inventoryProgram();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        starter.startStaging(stage);
    }
}
