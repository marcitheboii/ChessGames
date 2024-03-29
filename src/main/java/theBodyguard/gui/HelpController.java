package theBodyguard.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import startApp.GameController;

import java.io.IOException;

public class HelpController extends GameController {
    public void backToGame(MouseEvent event) {
        openThis("/theBodyguard/ui.fxml",event);
    }
}
