package sample;

import java.io.*;
import java.util.*;

public class CityDatabase {

    private ArrayList<String> cityList = null;

    private static final String cityFileName = "city.txt";
    private static CityDatabase instance = null;

    public static CityDatabase getInstance()
    {
        if(instance == null){
            instance = new CityDatabase();
        }
        return instance;
    }

    private CityDatabase ()
    {
        this.cityList = new ArrayList<>();
        BufferedReader br = (BufferedReader)IOHelper.getReader(cityFileName, IOHelper.ReaderType.BufferredFileReader);
        if (br != null)
        {
            String city;
            try {
                while ((city = br.readLine())!= null) {
                    cityList.add(city);
                }
            }catch( IOException e)
            {
                // TODO: apply logger
            }

        }
        cityList.sort((s1, s2)-> s1.compareTo(s2));
        IOHelper.closeReader(br);
    }

    public ArrayList<String> getCityList(){
        return cityList;
    }

    public boolean isCityExist(String city){
        return cityList.contains(city);
    }

    public ArrayList<String> getCityList(String city)
    {
        ArrayList<String> filtered = new ArrayList<>();
        for (String s : cityList)
        {
            if(s.startsWith(city)){
                filtered.add(s);
            }
        }
        return filtered;
    }
}

