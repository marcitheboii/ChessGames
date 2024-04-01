package landingPage;

import games.GameController;
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

public class LandingPageController extends GameController {
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
    private ImageView settingsIcon;

    @FXML
    private ImageView icon;

    @FXML
    private void initialize(){
        setImages();
    }

    private void setImages(){
        icon.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/icon.png")).toExternalForm()).getImage());

        lonelyKnightImage.setImage(new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/LK_opening.png"))).toExternalForm()).getImage());
        lonelyKnightImage.setOnMouseClicked(this::handleGoToLonelyKnight);

        maximalistKnightImage.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/MK_opening.png")).toExternalForm()).getImage());
        maximalistKnightImage.setOnMouseClicked(this::handleGoToMaximalistKnight);

        eightBishopsImage.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/EB_opening.png")).toExternalForm()).getImage());
        eightBishopsImage.setOnMouseClicked(this::handleGoToEightBishops);

        sixKnightsImage.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/SK_opening.png")).toExternalForm()).getImage());
        sixKnightsImage.setOnMouseClicked(this::handleGoToSixKnights);

        bodyGuardImage.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/TB_opening.png")).toExternalForm()).getImage());
        bodyGuardImage.setOnMouseClicked(this::handleGoToTheBodyguard);

        tightGameImage.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/TG_opening.png")).toExternalForm()).getImage());
        tightGameImage.setOnMouseClicked(this::handleGoToTightGame);

        settingsIcon.setImage(new ImageView(Objects.requireNonNull(LandingPageController.class.getResource("/images/setting.png")).toExternalForm()).getImage());
        settingsIcon.setOnMouseClicked(this::openSettings);
    }

    public void handleGoToLonelyKnight(javafx.scene.input.MouseEvent event) {
        openThis("/lonelyKnight/ui.fxml",event);
    }

    public void handleGoToMaximalistKnight(javafx.scene.input.MouseEvent event) {
        openThis("/maximalistKnight/ui.fxml",event);
    }

    public void handleGoToEightBishops(javafx.scene.input.MouseEvent event) {
        openThis("/eightBishops/ui.fxml",event);
    }

    public void handleGoToSixKnights(javafx.scene.input.MouseEvent event) {
        openThis("/sixKnights/ui.fxml",event);
    }

    public void handleGoToTheBodyguard(javafx.scene.input.MouseEvent event) {
        openThis("/theBodyguard/ui.fxml",event);
    }

    public void handleGoToTightGame(javafx.scene.input.MouseEvent event) {
        openThis("/tightGame/ui.fxml",event);
    }

    public void openSettings(javafx.scene.input.MouseEvent event) {
        openThis("/settings/settings.fxml",event);
    }
}
