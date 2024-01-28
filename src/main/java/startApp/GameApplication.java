package startApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameApplication extends Application {

    /**
     * Megnyitja a megadott fxml alapján az ablakot, majd a felhasználó imoutokat tovább adja a controllernek.
     *
     * @param stage melyik fxml-t szeretnénk kirjazolni
     * @throws IOException kivételt dob ha null paramétert kapna
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/landingPage/ui.fxml")));
        stage.setTitle("Chess Games");
        stage.getIcons().add(new Image(Objects.requireNonNull(GameApplication.class.getResourceAsStream("/landingPage/images/icon.png"))));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
