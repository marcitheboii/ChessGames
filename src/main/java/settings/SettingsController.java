package settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.css.RGBColor;
import settings.SettingsModel;
import startApp.LandingPageController;

import java.io.IOException;
import java.util.Objects;

public class SettingsController {

    private SettingsModel model = new SettingsModel();

    @FXML
    private ImageView backButton;
    @FXML
    private ImageView dk1;
    @FXML
    private ImageView dk2;
    @FXML
    private ImageView dk3;
    @FXML
    private ImageView dk4;
    @FXML
    private ImageView wk1;
    @FXML
    private ImageView wk2;
    @FXML
    private ImageView wk3;
    @FXML
    private ImageView wk4;
    @FXML
    private ImageView db1;
    @FXML
    private ImageView db2;
    @FXML
    private ImageView db3;
    @FXML
    private ImageView db4;
    @FXML
    private ImageView wb1;
    @FXML
    private ImageView wb2;
    @FXML
    private ImageView wb3;
    @FXML
    private ImageView wb4;
    @FXML
    private ImageView r1;
    @FXML
    private ImageView r2;
    @FXML
    private ImageView r3;
    @FXML
    private ImageView r4;
    @FXML
    private ImageView k1;
    @FXML
    private ImageView k2;
    @FXML
    private ImageView k3;
    @FXML
    private ImageView k4;
    @FXML
    private ColorPicker legalColor;


    @FXML
    private void initialize(){
        setImages();
    }

    private void setImages(){

        //hamburger menu button
        ImageView lonelyKnight = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/menu.png"))).toExternalForm());
        backButton.setImage(lonelyKnight.getImage());
        backButton.setOnMouseClicked(this::backToMenu);

        //DARK KNIGHTS
        String dk1_path = "dark_knight_filled.png";
        ImageView dk1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+dk1_path))).toExternalForm());
        dk1.setImage(dk1IMG.getImage());
        dk1.setOnMouseClicked(event -> setDarkKnight(dk1_path, event));

        String dk2_path = "dark_knight_filled1.png";
        ImageView dk2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+dk2_path))).toExternalForm());
        dk2.setImage(dk2IMG.getImage());
        dk2.setOnMouseClicked(event -> setDarkKnight(dk2_path, event));

        String dk3_path = "dark_knight_filled2.png";
        ImageView dk3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+dk3_path))).toExternalForm());
        dk3.setImage(dk3IMG.getImage());
        dk3.setOnMouseClicked(event -> setDarkKnight(dk3_path, event));

        String dk4_path = "dark_knight_filled3.png";
        ImageView dk4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+dk4_path))).toExternalForm());
        dk4.setImage(dk4IMG.getImage());
        dk4.setOnMouseClicked(event -> setDarkKnight(dk4_path, event));

        //WHITE KNIGHTS
        String wk1_path = "dark_knight_outline.png";
        ImageView wk1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wk1_path))).toExternalForm());
        wk1.setImage(wk1IMG.getImage());
        wk1.setOnMouseClicked(event -> setWhiteKnight(wk1_path, event));

        String wk2_path = "dark_knight_outline1.png";
        ImageView wk2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wk2_path))).toExternalForm());
        wk2.setImage(wk2IMG.getImage());
        wk2.setOnMouseClicked(event -> setWhiteKnight(wk2_path, event));

        String wk3_path = "dark_knight_outline2.png";
        ImageView wk3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wk3_path))).toExternalForm());
        wk3.setImage(wk3IMG.getImage());
        wk3.setOnMouseClicked(event -> setWhiteKnight(wk3_path, event));

        String wk4_path = "dark_knight_outline3.png";
        ImageView wk4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wk4_path))).toExternalForm());
        wk4.setImage(wk4IMG.getImage());
        wk4.setOnMouseClicked(event -> setWhiteKnight(wk4_path, event));

        //DARK BISHOP
        String db1_path = "dark_bishop_filled.png";
        ImageView db1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+db1_path))).toExternalForm());
        db1.setImage(db1IMG.getImage());
        db1.setOnMouseClicked(event -> setDarkBishop(db1_path, event));

        String db2_path = "dark_bishop_filled1.png";
        ImageView db2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+db2_path))).toExternalForm());
        db2.setImage(db2IMG.getImage());
        db2.setOnMouseClicked(event -> setDarkBishop(db2_path, event));

        String db3_path = "dark_bishop_filled2.png";
        ImageView db3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+db3_path))).toExternalForm());
        db3.setImage(db3IMG.getImage());
        db3.setOnMouseClicked(event -> setDarkBishop(db3_path, event));

        String db4_path = "dark_bishop_filled3.png";
        ImageView db4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+db4_path))).toExternalForm());
        db4.setImage(db4IMG.getImage());
        db4.setOnMouseClicked(event -> setDarkBishop(db4_path, event));

        //WHITE BISHOP
        String wb1_path = "dark_bishop_outline.png";
        ImageView wb1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wb1_path))).toExternalForm());
        wb1.setImage(wb1IMG.getImage());
        wb1.setOnMouseClicked(event -> setWhiteBishop(wb1_path, event));

        String wb2_path = "dark_bishop_outline1.png";
        ImageView wb2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wb2_path))).toExternalForm());
        wb2.setImage(wb2IMG.getImage());
        wb2.setOnMouseClicked(event -> setWhiteBishop(wb2_path, event));

        String wb3_path = "dark_bishop_outline2.png";
        ImageView wb3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wb3_path))).toExternalForm());
        wb3.setImage(wb3IMG.getImage());
        wb3.setOnMouseClicked(event -> setWhiteBishop(wb3_path, event));

        String wb4_path = "dark_bishop_outline3.png";
        ImageView wb4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+wb4_path))).toExternalForm());
        wb4.setImage(wb4IMG.getImage());
        wb4.setOnMouseClicked(event -> setWhiteBishop(wb4_path, event));

        //Rook
        String r1_path = "rook.png";
        ImageView r1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+r1_path))).toExternalForm());
        r1.setImage(r1IMG.getImage());
        r1.setOnMouseClicked(event -> setRook(r1_path, event));

        String r2_path = "rook1.png";
        ImageView r2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+r2_path))).toExternalForm());
        r2.setImage(r2IMG.getImage());
        r2.setOnMouseClicked(event -> setRook(r2_path, event));

        String r3_path = "rook2.png";
        ImageView r3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+r3_path))).toExternalForm());
        r3.setImage(r3IMG.getImage());
        r3.setOnMouseClicked(event -> setRook(r3_path, event));

        String r4_path = "rook3.png";
        ImageView r4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+r4_path))).toExternalForm());
        r4.setImage(r4IMG.getImage());
        r4.setOnMouseClicked(event -> setRook(r4_path, event));

        //King
        String k1_path = "king.png";
        ImageView k1IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+k1_path))).toExternalForm());
        k1.setImage(k1IMG.getImage());
        k1.setOnMouseClicked(event -> setKing(k1_path, event));

        String k2_path = "king1.png";
        ImageView k2IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+k2_path))).toExternalForm());
        k2.setImage(k2IMG.getImage());
        k2.setOnMouseClicked(event -> setKing(k2_path, event));

        String k3_path = "king2.png";
        ImageView k3IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+k3_path))).toExternalForm());
        k3.setImage(k3IMG.getImage());
        k3.setOnMouseClicked(event -> setKing(k3_path, event));

        String k4_path = "king3.png";
        ImageView k4IMG = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+k4_path))).toExternalForm());
        k4.setImage(k4IMG.getImage());
        k4.setOnMouseClicked(event -> setKing(k4_path, event));

        legalColor.setEditable(false);
        legalColor.setValue(model.getLegalColor());
    }
    private void setLegalColor(){
        model.setLegalColor(legalColor.getValue());
    }

    private void setDarkKnight(String path,javafx.scene.input.MouseEvent event){
        model.setDarkKnight(path);
    }

    private void setWhiteKnight(String path,javafx.scene.input.MouseEvent event){
        model.setWhiteKnight(path);
    }

    private void setDarkBishop(String path,javafx.scene.input.MouseEvent event){
        model.setDarkBishop(path);
    }

    private void setWhiteBishop(String path,javafx.scene.input.MouseEvent event){
        model.setWhiteBishop(path);
    }
    private void setRook(String path,javafx.scene.input.MouseEvent event){
        model.setRook(path);
    }

    private void setKing(String path,javafx.scene.input.MouseEvent event){
        model.setKing(path);
    }


    public void backToMenu(javafx.scene.input.MouseEvent event) {
        setLegalColor();
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/landingPage/ui.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
