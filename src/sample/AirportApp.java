package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AirportApp extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        ListJourneyScreen mainScreen = new ListJourneyScreen();
        mainScreen.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
