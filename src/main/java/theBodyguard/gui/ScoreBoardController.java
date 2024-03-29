package theBodyguard.gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import startApp.GameController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreBoardController extends GameController {
    public static class rowData {
        private final SimpleStringProperty time;
        private final SimpleIntegerProperty steps;
        private final SimpleBooleanProperty solved;
        private final SimpleStringProperty date;

        public rowData(String time, int steps, boolean solved, String date) {
            this.time = new SimpleStringProperty(time);
            this.steps = new SimpleIntegerProperty(steps);
            this.solved = new SimpleBooleanProperty(solved);
            this.date = new SimpleStringProperty(date);
        }

        // Getter methods for JavaFX properties
        public String getTime() {
            return time.get();
        }

        public int getSteps() {
            return steps.get();
        }

        public boolean isSolved() {
            return solved.get();
        }

        public String getDate() {
            return date.get();
        }
    }

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

    private final File myObj = new File("src/main/resources/theBodyguard/scoreboard.json");

    @FXML
    private void initialize() {
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
        initialize();
    }

    public void backToGame(MouseEvent event) {
        openThis("/theBodyguard/ui.fxml",event);
    }
}
