package scoreboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import games.GameController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ScoreBoardController extends GameController {
    private TableView<rowData> tableView;
    private TableColumn<rowData, String> time;
    private TableColumn<rowData, Integer> steps;
    private TableColumn<rowData, Boolean> solved;
    private TableColumn<rowData, String> created;
    private File myObj = new File("");

    public void setMyObj(File myObj) {
        this.myObj = myObj;
    }

    public void setTableView(TableView<rowData> tableView) {
        this.tableView = tableView;
    }

    public void setTime(TableColumn<rowData, String> time) {
        this.time = time;
    }

    public void setSteps(TableColumn<rowData, Integer> steps) {
        this.steps = steps;
    }

    public void setSolved(TableColumn<rowData, Boolean> solved) {
        this.solved = solved;
    }

    public void setCreated(TableColumn<rowData, String> created) {
        this.created = created;
    }

    public void handleScoreboard() {
        tableView.getItems().clear();

        if (myObj.exists()) {
            tableView.setId("my-table");
            try {
                Scanner scanner = new Scanner(myObj);
                JSONArray scoreboard = new JSONArray(scanner.nextLine());

                time.setCellValueFactory(new PropertyValueFactory<>("Time"));
                steps.setCellValueFactory(new PropertyValueFactory<>("Steps"));
                solved.setCellValueFactory(new PropertyValueFactory<>("Solved"));
                created.setCellValueFactory(new PropertyValueFactory<>("Date"));

                List<rowData> highScoreList = new ArrayList<>();

                for (Object obj : scoreboard) {
                    if (obj instanceof JSONObject) {
                        highScoreList.add(new rowData(((JSONObject) obj).getString("Time"), ((JSONObject) obj).getInt("Steps"), ((JSONObject) obj).getBoolean("Solved"), ((JSONObject) obj).getString("Date")));
                    }
                }

                ObservableList<rowData> observableResult = FXCollections.observableArrayList();
                observableResult.addAll(highScoreList);

                tableView.setItems(observableResult);

                scanner.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delScoreBoard(MouseEvent event) {
        myObj.delete();
        handleScoreboard();
    }
}
