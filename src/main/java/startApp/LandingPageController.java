package startApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LandingPageController {
    @FXML
    private HBox navBar;

    @FXML
    private ImageView lonelyKnightImage;
    @FXML
    private ImageView maximalistKnightImage;
    @FXML
    private ImageView eightBishopsImage;
    @FXML
    private ImageView sixKnightsImage;
    @FXML
    private ImageView bodyGuardImage;
    @FXML
    private ImageView tightGameImage;

    @FXML
    private void initialize(){
        setNavBar();
        setImages();
    }

    private void setNavBar(){
        ImageView icon = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/landingPage/images/icon.png")).toExternalForm());
        icon.setFitWidth(200);
        icon.setFitHeight(200);
        navBar.getChildren().add(icon);

    }

    private void setImages(){

        ImageView lonelyKnight = new ImageView((LandingPageController.class.getResource("/lonelyKnight/images/opening.png")).toExternalForm());
        lonelyKnightImage.setImage(lonelyKnight.getImage());
        lonelyKnightImage.setOnMouseClicked(this::handleGoToLonelyKnight);

        ImageView maximalistKnight = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/maximalistKnight/images/opening.png")).toExternalForm());
        maximalistKnightImage.setImage(maximalistKnight.getImage());
        maximalistKnightImage.setOnMouseClicked(this::handleGoToMaximalistKnight);

        ImageView eightBishops = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/eightBishops/images/opening.png")).toExternalForm());
        eightBishopsImage.setImage(eightBishops.getImage());
        eightBishopsImage.setOnMouseClicked(this::handleGoToEightBishops);

        ImageView sixKnights = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/sixKnights/images/opening.png")).toExternalForm());
        sixKnightsImage.setImage(sixKnights.getImage());
        sixKnightsImage.setOnMouseClicked(this::handleGoToSixKnights);

        ImageView bodyGuard = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/theBodyguard/images/opening.png")).toExternalForm());
        bodyGuardImage.setImage(bodyGuard.getImage());
        bodyGuardImage.setOnMouseClicked(this::handleGoToTheBodyguard);

        ImageView tightGame = new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/tightGame/images/opening.png")).toExternalForm());
        tightGameImage.setImage(tightGame.getImage());
        tightGameImage.setOnMouseClicked(this::handleGoToTightGame);

    }

    public void handleGoToLonelyKnight(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lonelyKnight/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoToMaximalistKnight(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/maximalistKnight/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoToEightBishops(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eightBishops/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoToSixKnights(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sixKnights/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoToTheBodyguard(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/theBodyguard/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGoToTightGame(javafx.scene.input.MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tightGame/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
