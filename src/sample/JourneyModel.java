package sample;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

/*TODO: what about price?*/
public class JourneyModel
{
    private SimpleStringProperty dateDeparture = null;
    private SimpleStringProperty dateArrival = null;
    private SimpleStringProperty cityDeparture = null;
    private SimpleStringProperty cityArrival = null;
    private SimpleIntegerProperty positions = null;
    private SimpleStringProperty id = null;

    public String getDateDeparture() {
        return dateDeparture.get();
    }

    public String getDateArrival() {
        return dateArrival.get();
    }

    public String getCityDeparture() {
        return cityDeparture.get();
    }

    public String getCityArrival() {
        return cityArrival.get();
    }

    public String getId()
    {
        return id.get().substring(id.get().length() - GUIHelper.idLenght);
    }

    public int getPositions()
    {
        return positions.get();
    }

    public String getFullId()
    {
        return id.get();
    }

    public JourneyModel(Journey journey)
    {
        dateDeparture = new SimpleStringProperty(GUIHelper.dateToString(journey.getDataDeparture()));
        dateArrival = new SimpleStringProperty(GUIHelper.dateToString(journey.getDataArrival()));
        cityDeparture = new SimpleStringProperty(journey.getDepartureCity());
        cityArrival = new SimpleStringProperty(journey.getArrivalCity());
        positions = new SimpleIntegerProperty((journey.getPositionCount()));
        id = new SimpleStringProperty(journey.getId().toString());
    }

}