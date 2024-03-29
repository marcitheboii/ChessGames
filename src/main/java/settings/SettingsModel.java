package settings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.paint.Color;
import org.w3c.dom.css.RGBColor;

public class SettingsModel {

    private String whiteKnight = "";
    private String darkKnight = "";
    private String whiteBishop = "";
    private String darkBishop = "";
    private String rook = "";
    private String king = "";

    //255,229,0
    private Color legalColor = Color.rgb(255,229,0,0.7);

    File file = new File("src/main/resources/settings/gameSettings.json");

    public SettingsModel(){
        loadSettings();
    }

    private void loadSettings(){

        try {
            Scanner scanner = new Scanner(file);
            JSONObject settings = new JSONObject(scanner.nextLine());

            setDarkKnight((String) settings.get("darkKnight"));
            setWhiteKnight((String) settings.get("whiteKnight"));
            setDarkBishop((String) settings.get("darkBishop"));
            setWhiteBishop((String) settings.get("whiteBishop"));
            setRook((String) settings.get("rook"));
            setKing((String) settings.get("king"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWhiteKnight() {
        return whiteKnight;
    }

    public void setWhiteKnight(String whiteKnight) {
        updateSettings("whiteKnight",whiteKnight);
        this.whiteKnight = whiteKnight;
    }

    public String getDarkKnight() {
        return darkKnight;
    }

    public void setDarkKnight(String darkKnight) {
        updateSettings("darkKnight",darkKnight);

        this.darkKnight = darkKnight;
    }

    public String getWhiteBishop() {
        return whiteBishop;
    }

    public void setWhiteBishop(String whiteBishop) {
        updateSettings("whiteBishop",whiteBishop);

        this.whiteBishop = whiteBishop;
    }

    public String getDarkBishop() {
        return darkBishop;
    }

    public void setDarkBishop(String darkBishop) {
        updateSettings("darkBishop",darkBishop);
        this.darkBishop = darkBishop;
    }

    public String getRook() {
        return rook;
    }

    public void setRook(String rook) {
        updateSettings("rook",rook);
        this.rook = rook;
    }

    public String getKing() {
        return king;
    }

    public void setKing(String king) {
        updateSettings("king",king);
        this.king = king;
    }

    public Color getLegalColor() {
        return legalColor;
    }

    public void setLegalColor(Color legalColor) {
        updateSettings("legalColor",legalColor.toString());
        this.legalColor = legalColor;
    }

    private void updateSettings(String figure, String path){
        try {
            Scanner scanner = new Scanner(file);
            JSONObject settings = new JSONObject(scanner.nextLine());
            settings.put(figure, path);

            FileWriter write = new FileWriter(file);
            write.write(settings.toString());

            scanner.close();
            write.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
