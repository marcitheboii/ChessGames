package games.eightBishops.gui;

import javafx.scene.input.MouseEvent;
import games.GameController;

public class HelpController extends GameController {
    public void backToGame(MouseEvent event) {
        openThis("/eightBishops/ui.fxml",event);
    }
}
