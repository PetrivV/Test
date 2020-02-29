package sample;

import java.io.*;
import java.util.*;

public class AirportManager extends SubjectBase<IJourneyListener>
{
    private static AirportManager instance = null;

    private HashMap<UUID, Journey> journeys = new HashMap<>();

    public static AirportManager getInstance() {
        if (instance == null) {
            instance = new AirportManager();
        }
        return instance;
    }

    private AirportManager() { }

    public void add(Journey journey)
    {
        UUID id = journey.getId();
        if (!journeys.containsKey(id)) {
            journeys.put(id, journey);
            updateAll(journey, IListener.Action.Add);
        }
    }

    public void download()
    {
        Writer writer = IOHelper.getWriter(IOHelper.jsondbFile);

        if(writer != null)
        {
            for(Journey j : journeys.values( ))
                IOHelper.writeJson( j, writer );

            IOHelper.closeWriter( writer );
        }
    }

    public void upload()
    {
        Reader fileReader = IOHelper.getReader(IOHelper.jsondbFile,
                                                IOHelper.ReaderType.SimpleFileReader);

        if (fileReader != null)
        {
            Journey j = IOHelper.readJson(fileReader);
            while(j != null) {
                add(j);
                j = IOHelper.readJson(fileReader);
            }

            IOHelper.closeReader(fileReader);
        }
    }
    public void book(UUID id, int position)
    {
        Journey journey = journeys.get(id);
        if(journey != null){
           ArrayList<Position> positions = journey.getPositions();
           Position pos =  positions.get(position -1);
           if(!pos.isBooked()){
               pos.setBooked(true);
               updateAll(journey, IListener.Action.Update);
           }
        }


    }
    public Journey getJourney(UUID id){
        return journeys.get(id);
    }

    // TODO: need some class that will do this
   /* public ArrayList<Journey> search(SearchOption searchOption, String searchStr)
    {
        Filter<Journey> filter = new Filter<>();
        Specification<Journey> specification = null;

        if(searchOption == SearchOption.CityDeparture || searchOption == SearchOption.CityArrival )
        {
            specification = new CitySpecification(searchStr, searchOption == SearchOption.CityDeparture);
        }
        else {
            DateFormat df = new SimpleDateFormat("MM dd");
            Date result = null;

            try{
                result = df.parse(searchStr);
            }catch (ParseException e){
                return null;
            }

            specification = new DateSpecification(result, SearchOption.DateDeparture == searchOption);
        }
        return filter.filter(journeys.values(),specification );
        }*/


    public void remove(UUID uuid)
    {
        Journey removedJourney = journeys.remove(uuid);
        if(removedJourney != null)
            updateAll(removedJourney, IListener.Action.Remove);
    }

    public void update(Journey journey)
    {
        journeys.replace(journey.getId(),journey);
        updateAll(journey, IListener.Action.Update);
    }


    public ArrayList<Journey> getJourneysList()
    {
        ArrayList<Journey> jl = new ArrayList<>( journeys.values());
        Comparator<Journey> comparator = (o1, o2)->o1.getDataDeparture().compareTo(o2.getDataDeparture());
        jl.sort(comparator);
        return jl;
    }

    // TODO: in future we will make separate class for searching
    public enum SearchOption {DateArrival, DateDeparture, CityDeparture, CityArrival};
}
