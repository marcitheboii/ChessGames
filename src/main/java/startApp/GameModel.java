package startApp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class GameModel {

    public void saveData(String gameName,String time,int steps,boolean solved) {
        try {

            File myObj = new File("src/main/resources/"+gameName+"/scoreboard.json");

            if (myObj.exists()) {
                Scanner scanner = new Scanner(myObj);
                JSONArray scoreboard = new JSONArray(scanner.nextLine());

                scoreboard.put(creteNewScore(time,steps,solved));

                FileWriter fileWriter = new FileWriter(myObj);
                fileWriter.write(scoreboard.toString());
                fileWriter.close();
                scanner.close();
            } else {
                FileWriter fileWriter = new FileWriter(myObj);
                JSONArray scoreBoard = new JSONArray();

                scoreBoard.put(creteNewScore(time,steps,solved));

                fileWriter.write(scoreBoard.toString());
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String FormattedDateTimeNow(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private JSONObject creteNewScore(String time,int steps,Boolean solved){
        JSONObject newScore = new JSONObject();
        newScore.put("Time", time);
        newScore.put("Steps", steps);
        newScore.put("Date", FormattedDateTimeNow());
        newScore.put("Solved", solved);

        return newScore;
    }
}
