package settings;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.css.RGBColor;
import settings.SettingsModel;
import startApp.GameController;
import startApp.LandingPageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsController extends GameController {

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

    private List<FxmlElementWithPath> fxmlElements = new ArrayList<>();

    private static class FxmlElementWithPath {
        private ImageView element;
        private String path;

        public FxmlElementWithPath(ImageView element, String path) {
            this.element = element;
            this.path = path;
        }

        public ImageView getElement() {
            return element;
        }

        public String getPath() {
            return path;
        }
    }


    @FXML
    private void initialize(){

        //Dark Knights
        fxmlElements.add(new FxmlElementWithPath(dk1,"dark_knight_filled.png"));
        fxmlElements.add(new FxmlElementWithPath(dk2,"dark_knight_filled1.png"));
        fxmlElements.add(new FxmlElementWithPath(dk3,"dark_knight_filled2.png"));
        fxmlElements.add(new FxmlElementWithPath(dk4,"dark_knight_filled3.png"));

        //White Knights
        fxmlElements.add(new FxmlElementWithPath(wk1,"dark_knight_outline.png"));
        fxmlElements.add(new FxmlElementWithPath(wk2,"dark_knight_outline1.png"));
        fxmlElements.add(new FxmlElementWithPath(wk3,"dark_knight_outline2.png"));
        fxmlElements.add(new FxmlElementWithPath(wk4,"dark_knight_outline3.png"));

        //Dark Bishops
        fxmlElements.add(new FxmlElementWithPath(db1,"dark_bishop_filled.png"));
        fxmlElements.add(new FxmlElementWithPath(db2,"dark_bishop_filled1.png"));
        fxmlElements.add(new FxmlElementWithPath(db3,"dark_bishop_filled2.png"));
        fxmlElements.add(new FxmlElementWithPath(db4,"dark_bishop_filled3.png"));

        //White Bishops
        fxmlElements.add(new FxmlElementWithPath(wb1,"dark_bishop_outline.png"));
        fxmlElements.add(new FxmlElementWithPath(wb2,"dark_bishop_outline1.png"));
        fxmlElements.add(new FxmlElementWithPath(wb3,"dark_bishop_outline2.png"));
        fxmlElements.add(new FxmlElementWithPath(wb4,"dark_bishop_outline3.png"));

        //Rook
        fxmlElements.add(new FxmlElementWithPath(r1,"rook.png"));
        fxmlElements.add(new FxmlElementWithPath(r2,"rook1.png"));
        fxmlElements.add(new FxmlElementWithPath(r3,"rook2.png"));
        fxmlElements.add(new FxmlElementWithPath(r4,"rook3.png"));

        //King
        fxmlElements.add(new FxmlElementWithPath(k1,"king.png"));
        fxmlElements.add(new FxmlElementWithPath(k2,"king1.png"));
        fxmlElements.add(new FxmlElementWithPath(k3,"king2.png"));
        fxmlElements.add(new FxmlElementWithPath(k4,"king3.png"));

        setImages();
    }

    private void setImages(){
        ImageView lonelyKnight = new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/menu.png"))).toExternalForm());
        backButton.setImage(lonelyKnight.getImage());
        backButton.setOnMouseClicked(this::backToMenu);

        for (var pair : fxmlElements) {
            pair.getElement().setImage(new ImageView((Objects.requireNonNull(LandingPageController.class.getResource("/images/"+pair.getPath()))).toExternalForm()).getImage());

            switch (pair.getElement().getStyleClass().get(1)){
                case "darkKnight":
                    pair.getElement().setOnMouseClicked(event -> setDarkKnight(pair.getPath(), event));
                    if(model.getDarkKnight().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
                case "whiteKnight":
                    pair.getElement().setOnMouseClicked(event -> setWhiteKnight(pair.getPath(), event));
                    if(model.getWhiteKnight().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
                case "darkBishop":
                    pair.getElement().setOnMouseClicked(event -> setDarkBishop(pair.getPath(), event));
                    if(model.getDarkBishop().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
                case "whiteBishop":
                    pair.getElement().setOnMouseClicked(event -> setWhiteBishop(pair.getPath(), event));
                    if(model.getWhiteBishop().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
                case "rook":
                    pair.getElement().setOnMouseClicked(event -> setRook(pair.getPath(), event));
                    if(model.getRook().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
                case "king":
                    pair.getElement().setOnMouseClicked(event -> setKing(pair.getPath(), event));
                    if(model.getKing().equals(pair.getPath())) {
                        giveBorder(pair.getElement());
                    } else {
                        removeBorder(pair.getElement());
                    }
                    break;
            }
        }

        legalColor.setEditable(false);
        legalColor.setValue(model.getLegalColor());
    }

    private void setLegalColor(){
        model.setLegalColor(legalColor.getValue());
    }

    private void setDarkKnight(String path,javafx.scene.input.MouseEvent event){
        model.setDarkKnight(path);
        setImages();
    }

    private void setWhiteKnight(String path,javafx.scene.input.MouseEvent event){
        model.setWhiteKnight(path);
        setImages();
    }

    private void setDarkBishop(String path,javafx.scene.input.MouseEvent event){
        model.setDarkBishop(path);
        setImages();
    }

    private void setWhiteBishop(String path,javafx.scene.input.MouseEvent event){
        model.setWhiteBishop(path);
        setImages();
    }
    private void setRook(String path,javafx.scene.input.MouseEvent event){
        model.setRook(path);
        setImages();
    }

    private void setKing(String path,javafx.scene.input.MouseEvent event){
        model.setKing(path);
        setImages();
    }

    private void giveBorder(ImageView img){
        img.getParent().setStyle(
                "-fx-border-color: black; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px;"
        );
    }

    private void removeBorder(ImageView img){
        img.getParent().setStyle("");
    }


    public void backToMenu(MouseEvent event) {
        setLegalColor();
        openThis("/landingPage/ui.fxml",event);
    }
}
