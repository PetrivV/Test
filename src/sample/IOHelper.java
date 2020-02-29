package sample;
import org.json.simple.*;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.sql.*;
import org.json.simple.parser.*;

public class IOHelper
{
    public static final String jsondbFile = "journeys.json";

    public enum ReaderType {SimpleFileReader, BufferredFileReader};

    public static void writeJson(Journey j, Writer writer) {

        JSONObject jo = new JSONObject();
        jo.put("id", j.getId().toString());
        jo.put("dateDeparture", GUIHelper.dateToString(j.getDataDeparture()));
        jo.put("dateArrival", GUIHelper.dateToString(j.getDataDeparture()));
        jo.put("departureCity", j.getDepartureCity());
        jo.put("arrivalCity", j.getArrivalCity());
        jo.put("positionsCount",String.valueOf(j.getPositionCount()));
        jo.put("positions",j.getPositions());

        try {
            writer.write(jo.toJSONString());
        }catch( IOException e)
        {

            // TODO: apply logger
        }
    }

    public static Journey readJson(Reader fileReader) {

        Object obj = null;
        try {
            obj = new JSONParser().parse(fileReader);
        }
        catch (ParseException e) {
            System.out.println(e);
            return null;
        }catch (IOException e) {
            System.out.println ( e );
            return null;
        }

        JSONObject jo = (JSONObject) obj;
        String id = (String) jo.get("id");
        String dateDeparture = (String) jo.get("dateDeparture");
        String dateArrival = (String) jo.get("dateArrival");
        String departureCity = (String) jo.get("departureCity");
        String arrivalCity = (String) jo.get("arrivalCity");
        String positionsCount = (String) jo.get("positionsCount");
        ArrayList positions = (ArrayList<Long>) jo.get("positions");

        Journey journey = new Journey(GUIHelper.stringToDate(dateDeparture), GUIHelper.stringToDate(dateArrival),
                departureCity, arrivalCity, Integer.parseInt(positionsCount));

        journey.setId(UUID.fromString(id));
        journey.setPositions(positions);

        return journey;
    }

    // TODO: need to apply the same thing as for Reader
    public static Writer getWriter(String fileName) {
        try {
            return new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            // TODO: need apply logger for this case
            return null;
        }
    }

    public static Reader getReader(String fileName, ReaderType type) {
        try {
           switch(type)
           {
               case BufferredFileReader:
                   return new BufferedReader(new FileReader(fileName));
               case SimpleFileReader:
                   return new FileReader(fileName);
               default:
                   return null;
           }
        }catch (FileNotFoundException e) {
            // TODO: need apply logger for this case
            return null;
        }
    }

    public static void closeReader(Reader reader)
    {
        try{
            reader.close();
        }catch( IOException e){
            // TODO: need apply logger for this case
        }
    }
    public static void closeWriter(Writer writer)
    {
        try{
            writer.close();
        }catch( IOException e)
        {
            //TODO: apply logger
        }
    }
}



