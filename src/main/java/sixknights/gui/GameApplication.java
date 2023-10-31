package sixknights.gui;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

    /**
     * Megnyitja a megadott fxml alapján az ablakot, majd a felhasználó imoutokat tovább adja a controllernek.
     *
     * @param stage melyik fxml-t szeretnénk kirjazolni
     * @throws IOException kivételt dob ha null paramétert kapna
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui.fxml")));
        stage.setTitle("Six Knights Problem");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
