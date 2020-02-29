package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class JourneyInfoScreen  extends CommonScreen
{
    private VBox mainLayout = null;

    private Button doButton = null;
    private Button cancelButton = null;

    private TextField departureDateField = null;
    private TextField arrivalDateField = null;
    private TextField positionsCountField = null;

    private ComboBox<String> depCityComboBox = null;
    private ComboBox<String> arrCityComboBox = null;

    public enum ScreenType{Add, Edit}

    private ScreenType screenType;

    private Journey editedJourney = null;

    public JourneyInfoScreen(ScreenType screenType)
    {
        this.screenType = screenType;

        initComboBoxes();
        initTextField();
        initButtons();
        initGridPane();
        initBindings();
        initScene(mainLayout,mainLayout.getMaxWidth(),mainLayout.getMaxHeight());
    }
    /*
    * TODO: need to make separate class for styles
    * and move all code that relates to style into it
    * */
    private void initGridPane(){
        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Departure Date"),0,0);
        gridPane.add(new Label("Arrival Date"),0,1);
        gridPane.add(departureDateField,1,0);
        gridPane.add(arrivalDateField,1,1);

        gridPane.add(new Label("Departure City"),0,2);
        gridPane.add(new Label("Arrival City"),0,3);
        gridPane.add(depCityComboBox,1,2);
        gridPane.add(arrCityComboBox,1,3);

        gridPane.add(new Label("Position Count"),0,4);
        gridPane.add(positionsCountField,1,4);

        gridPane.setHgap(20);
        gridPane.setVgap(30);
        gridPane.setStyle("-fx-padding: 25;-fx-alignment: center;");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(doButton,cancelButton);
        hBox.setPadding((new Insets(12,15,12,15)));
        hBox.setSpacing(30);

        mainLayout = new VBox();
        mainLayout.getChildren().addAll(gridPane,hBox);
    }

    private void initButtons(){

        doButton = new Button(screenType == ScreenType.Add ? "Add" : "Edit");
        HBox.setHgrow(doButton, Priority.ALWAYS);
        doButton.setMaxWidth(Double.MAX_VALUE);

        cancelButton = new Button("Cancel");
        HBox.setHgrow( cancelButton ,Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        doButton.setOnMouseClicked((MouseEvent action) ->
        {
                Date depDate = GUIHelper.stringToDate(departureDateField.getText());
                Date arrDate = GUIHelper.stringToDate(arrivalDateField.getText());

                String cityDep = depCityComboBox.getValue();
                String cityArr = arrCityComboBox.getValue();
                int positions = Integer.parseInt(positionsCountField.getText());

                GUIHelper.ErrorType error = GUIHelper.checkDatum(depDate,arrDate,cityDep,cityArr);
                if(error == GUIHelper.ErrorType.None)
                {
                    if(screenType == ScreenType.Add)
                        AirportManager.getInstance().add(new Journey(depDate,arrDate,cityDep,cityArr,positions));
                    else {
                        editedJourney.setDataDeparture(depDate);
                        editedJourney.setDataArrival(arrDate);
                        editedJourney.setDepartureCity(cityDep);
                        editedJourney.setArrivalCity(cityArr);
                        editedJourney.setPositionsCount(positions);
                        AirportManager.getInstance().update(editedJourney);
                    }
                    stage.close();
                }
                else {
                    Alert alert = GUIHelper.createAlert(error);
                    alert.showAndWait().ifPresent((act)->alert.close());
                }
        });

        cancelButton.setOnMouseClicked((MouseEvent action) -> {

                Alert alert = GUIHelper.createAlert(Alert.AlertType.CONFIRMATION,
                        "WARNING",
                        "Changes won't be applied",
                        "Do you won't to continue?",
                        ButtonType.YES,ButtonType.NO);

                alert.showAndWait().ifPresent((ButtonType actionButton) -> {
                    if (actionButton == ButtonType.YES)  stage.close();
                    else                                 alert.close();
                });
        });

    }

    private  void initBindings()
    {
        BooleanBinding binding = Bindings.or(
                departureDateField.textProperty().isEmpty(),
                arrivalDateField.textProperty().isEmpty()).
                or(depCityComboBox.valueProperty().isNull()).
                or(arrCityComboBox.valueProperty().isNull()).
                or(positionsCountField.textProperty().isEmpty());

        doButton.disableProperty().bind(binding);
    }

    private void initComboBoxes()
    {
        ArrayList<String> cityList = CityDatabase.getInstance().getCityList();

        depCityComboBox = new ComboBox<>(FXCollections.observableArrayList (cityList));
        arrCityComboBox = new ComboBox<>(FXCollections.observableArrayList (cityList));

        depCityComboBox.setEditable(true);
        arrCityComboBox.setEditable(true);

        depCityComboBox.getEditor().textProperty().addListener(new CityComboBoxChangeListener(depCityComboBox));
        arrCityComboBox.getEditor().textProperty().addListener(new CityComboBoxChangeListener(arrCityComboBox));
    }

    private void initTextField()
    {
        arrivalDateField = new TextField();
        departureDateField = new TextField();
        positionsCountField = new TextField();

        arrivalDateField.setEditable(false);
        departureDateField.setEditable(false);


        positionsCountField.addEventFilter(KeyEvent.KEY_TYPED,
          new TypedKeyEventFilter(GUIHelper.numberRegex));
        arrivalDateField.addEventFilter(MouseEvent.MOUSE_CLICKED,
          new DateFieldEventHandler(arrivalDateField));
        departureDateField.addEventFilter(MouseEvent.MOUSE_CLICKED,
          new DateFieldEventHandler(departureDateField));
    }

    public void setJourneyInfo(Journey journey)
    {
       departureDateField.setText(GUIHelper.dateToString(journey.getDataDeparture()));
       arrivalDateField.setText(GUIHelper.dateToString(journey.getDataArrival()));

       depCityComboBox.getItems().setAll(journey.getDepartureCity());
       arrCityComboBox.getItems().setAll(journey.getDepartureCity());

       positionsCountField.setText(String.valueOf(journey.getPositionCount()));

       editedJourney = journey;
    }
}

class CityComboBoxChangeListener implements ChangeListener<String>
{
    private ComboBox<String> comboBox;

    public CityComboBoxChangeListener(ComboBox<String> comboBox ) {
        this.comboBox = comboBox;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if(t1.isEmpty()){
            comboBox.setItems(FXCollections.observableArrayList(CityDatabase.getInstance().getCityList()));
        }else if(!s.equals(t1)){
            comboBox.setItems(FXCollections.observableArrayList (CityDatabase.getInstance().getCityList(t1)));
        }
    }
}

class DateFieldEventHandler implements EventHandler<MouseEvent>{

    private TextField dateField;

    DateFieldEventHandler(TextField dateField){ this.dateField = dateField; }

    @Override
    public void handle(MouseEvent mouseEvent)
    {
        DateTimeSelectDialog dialog = new DateTimeSelectDialog();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.OK) {
           dateField.setText(dialog.getDateTime());
        }
    }
}

class TypedKeyEventFilter implements EventHandler<KeyEvent>{

    private String acceptedKeys;

    TypedKeyEventFilter(String acceptedKeys){
        this.acceptedKeys = acceptedKeys;
    }

    @Override
    public void handle(KeyEvent keyEvent)
    {
        if (keyEvent.getEventType () == KeyEvent.KEY_TYPED)
        {
            if (!acceptedKeys.contains ( keyEvent.getCharacter () ))
                keyEvent.consume ();
        }
    }
}