package sample;

import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;


public class BookedScreen extends CommonScreen {

    private BorderPane border = null;

    private ArrayList<Label> labels = null;

    private final static int rightColumns = 3;
    private final static int leftColumns = 3;

    private HBox hBox = null;

    public BookedScreen(Journey journey) {

        initLabels(journey);
        initLayout();
        initGridPane();
        initScene(border, border.getMaxWidth(), border.getMaxHeight());
    }
    private void initGridPane(){
        GridPane gridPaneRight = new GridPane();
        GridPane gridPaneLeft = new GridPane();

        gridPaneLeft.setGridLinesVisible(true);
        gridPaneLeft.setStyle("-fx-padding: 15;-fx-alignment: center;");

        gridPaneRight.setGridLinesVisible(true);
        gridPaneRight.setStyle("-fx-padding: 15;-fx-alignment: center;");


        int row = 0;

        for (int i = 0; i < labels.size(); row++) {
            for (int j = 0; j < leftColumns && i < labels.size(); j++, i++) {
                gridPaneLeft.add(labels.get(i), j, row);
            }
            for (int k = 0; k < rightColumns && i < labels.size(); k++, i++) {
                gridPaneRight.add(labels.get(i), k, row);
            }
        }
        hBox.getChildren().addAll(gridPaneLeft, gridPaneRight);
    }
    private void initLayout() {

        Text title = new Text();
        title.setText("Plane");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        hBox = new HBox();
        hBox.setStyle("-fx-border-color: black;-fx-border-width: 3");
        hBox.setPadding((new Insets(12, 15, 12, 15)));
        hBox.setSpacing(30);

        border = new BorderPane();
        border.setCenter(hBox);
        border.setTop(title);

    }

    private void initLabels(Journey journey) {

        labels = new ArrayList<>();

        ArrayList<Position> positions = journey.getPositions();

        for (int i = 0; i < positions.size(); i++) {

            PositionLabel positionLabel = new PositionLabel(positions.get(i));

            positionLabel.setOnMouseClicked((event) -> {

                if (event.getClickCount() == 2) {
                    if (positionLabel.getPosition().isBooked()) {
                        Alert alert = GUIHelper.createAlert(Alert.AlertType.WARNING, "Booked",
                                "position booked", "This position already booked", ButtonType.OK);
                        alert.showAndWait().ifPresent((ButtonType actionButton)->{
                            alert.close();
                        });
                    } else {
                        Alert alert = GUIHelper.createAlert(Alert.AlertType.INFORMATION, "Booked",
                                "Position booked", "You booked position №" +positionLabel.getPosition().getNumberPosition(), ButtonType.OK);

                        alert.showAndWait().ifPresent((ButtonType actionButton) -> {
                            alert.close();
                            stage.close();
                        });
                        AirportManager.getInstance().book(journey.getId(),positionLabel.getPosition().getNumberPosition());
                    }

                }
            });
            labels.add(i,positionLabel);
        }
    }
}