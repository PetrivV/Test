package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.UUID;

public class ListJourneyScreen extends CommonScreen {

    private enum Columns {ID, DateDeparture, DateArrival, CityArrival, CityDeparture, ColumnsCount}

    private enum Buttons {Add, Remove, Edit, Exit, Book, ButtonsCount}


    private TableView<JourneyModel> tableView = null;
    private Button[] buttons = new Button[Buttons.ButtonsCount.ordinal()];


    private static Pair<String, String> columnsName[] = new Pair[Columns.ColumnsCount.ordinal()];

    static {
        columnsName[Columns.ID.ordinal()] = new Pair<>("ID", "id");
        columnsName[Columns.DateDeparture.ordinal()] = new Pair<>("Date Departure", "dateDeparture");
        columnsName[Columns.DateArrival.ordinal()] = new Pair<>("Date Arrival", "dateArrival");
        columnsName[Columns.CityArrival.ordinal()] = new Pair<>("City Departure", "cityDeparture");
        columnsName[Columns.CityDeparture.ordinal()] = new Pair<>("City Arrival", "cityArrival");
    }

    ;

    public ListJourneyScreen() {

        initTableView();
        initButtons();
        initLayouts();
        initList();

        stage.setOnShowing((windowEvent) -> {
            AirportManager.getInstance().upload();
        });
        stage.setOnCloseRequest((windowEvent) -> {
            AirportManager.getInstance().download();
        });
    }

    private void initList() {
        ArrayList<Journey> list = AirportManager.getInstance().getJourneysList();
        ObservableList<JourneyModel> models = FXCollections.observableArrayList();

        for (Journey j : list) {
            models.add(new JourneyModel(j));
        }

        tableView.setItems(models);
    }

    private void initTableView() {
        tableView = new TableView<>();

        TableColumn<JourneyModel, String> columns[] = new TableColumn[Columns.ColumnsCount.ordinal()];

        for (int col = Columns.ID.ordinal(); col < Columns.ColumnsCount.ordinal(); ++col) {
            columns[col] = new TableColumn<>(columnsName[col].getKey());
            columns[col].setCellValueFactory(new PropertyValueFactory<>(columnsName[col].getValue()));
        }

        tableView.setEditable(false);
        tableView.getColumns().addAll(columns);

    }

    /*TODO: as we discussed before need separate *.css file for style!*/
    private void initButtons() {
        buttons[Buttons.Add.ordinal()] = new Button("Add");
        buttons[Buttons.Exit.ordinal()] = new Button("Exit");
        buttons[Buttons.Edit.ordinal()] = new Button("Edit");
        buttons[Buttons.Remove.ordinal()] = new Button("Remove");
        buttons[Buttons.Book.ordinal()] = new Button(("Book"));

        for (Button button : buttons) {
            button.setStyle("-fx-background-radius: 30;");
            HBox.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
        }

        buttons[Buttons.Add.ordinal()].setOnMouseClicked((event) -> {
            JourneyInfoScreen addJourneyScreen = new JourneyInfoScreen(JourneyInfoScreen.ScreenType.Add);
            addJourneyScreen.show();
        });

        buttons[Buttons.Edit.ordinal()].setOnMouseClicked((event) -> {
            JourneyModel item = tableView.getSelectionModel().getSelectedItem();
            if (item == null)
                return;
            JourneyInfoScreen editJourneyScreen = new JourneyInfoScreen(JourneyInfoScreen.ScreenType.Edit);
            editJourneyScreen.setJourneyInfo(AirportManager.getInstance().getJourney(UUID.fromString(item.getFullId())));
            editJourneyScreen.show();
        });

        buttons[Buttons.Exit.ordinal()].setOnMouseClicked((event) -> stage.close());

        buttons[Buttons.Remove.ordinal()].setOnMouseClicked((event) -> {
            JourneyModel item = tableView.getSelectionModel().getSelectedItem();
            if (item == null)
                return;
            AirportManager.getInstance().remove(UUID.fromString(item.getFullId()));
        });
        buttons[Buttons.Book.ordinal()].setOnMouseClicked((event) -> {
            JourneyModel item = tableView.getSelectionModel().getSelectedItem();
            if (item == null)
                return;
            BookedScreen bookedScreen = new BookedScreen(AirportManager.getInstance().getJourney(UUID.fromString(item.getFullId())));
            bookedScreen.show();
        });
    }

    // TODO: the same as with addJourneyScreen need separate style class
    private void initLayouts() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(12, 15, 12, 15));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(buttons);

        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(hBox, 0.0);
        AnchorPane.setRightAnchor(hBox, 0.0);
        AnchorPane.setBottomAnchor(hBox, 0.0);
        anchorPane.getChildren().addAll(tableView, hBox);

        initScene(anchorPane, anchorPane.getMaxWidth(), anchorPane.getMaxHeight());
    }

    @Override
    public void update(Object j, Action action) {
        Journey journey = (Journey) j;
        if (action == Action.Add)
            tableView.getItems().add(new JourneyModel(journey));
        else if (action == Action.Remove)
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        else if (action == Action.Update) {
            ObservableList<JourneyModel> observableList = tableView.getItems();
            int i;
            for (i = 0; i < observableList.size(); i++) {
                if (observableList.get(i).getFullId().equals(journey.getId().toString())) {
                    break;
                }
            }
            if (observableList.size() != i) {
                observableList.remove(i);
                observableList.add(i, new JourneyModel(journey));
            }

        }
    }
}