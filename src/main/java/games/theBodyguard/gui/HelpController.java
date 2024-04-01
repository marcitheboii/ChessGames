package games.theBodyguard.gui;

import javafx.scene.input.MouseEvent;
import games.GameController;

public class HelpController extends GameController {
    public void backToGame(MouseEvent event) {
        openThis("/theBodyguard/ui.fxml",event);
    }
}
