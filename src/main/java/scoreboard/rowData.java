package scoreboard;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class rowData {
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
