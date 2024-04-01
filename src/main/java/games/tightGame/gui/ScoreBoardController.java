package games.tightGame.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import scoreboard.rowData;

import java.io.File;

public class ScoreBoardController extends scoreboard.ScoreBoardController {

    @FXML
    private Label scoreBoard;
    @FXML
    private TableView<rowData> tableView;

    @FXML
    private TableColumn<rowData, String> time;

    @FXML
    private TableColumn<rowData, Integer> steps;

    @FXML
    private TableColumn<rowData, Boolean> solved;

    @FXML
    private TableColumn<rowData, String> created;
    private final File myObj = new File("src/main/resources/tightGame/scoreboard.json");

    @FXML
    private void initialize() {
        setTableView(tableView);
        setCreated(created);
        setSolved(solved);
        setMyObj(myObj);
        setSteps(steps);
        setTime(time);
        handleScoreboard();
    }

    public void backToGame(MouseEvent event) {
        openThis("/tightGame/ui.fxml",event);
    }
}
