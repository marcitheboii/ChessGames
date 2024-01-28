package startApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button goToLonelyKnight;
    @FXML
    private Button goToMaximalistKnight;
    @FXML
    private Button goToEightBishops;
    @FXML
    private Button goToSixKnights;
    @FXML
    private Button goToTheBodyguard;
    @FXML
    private Button goToTightGame;

    @FXML
    private void initialize(){
    }

    public void handleGoToLonelyKnight(final ActionEvent actionEvent) throws IOException {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lonelyKnight/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
    }

    public void handleGoToMaximalistKnight(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/maximalistKnight/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleGoToEightBishops(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eightBishops/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleGoToSixKnights(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sixKnights/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleGoToTheBodyguard(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/theBodyguard/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleGoToTightGame(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tightGame/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
