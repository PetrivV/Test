package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Journey implements Cloneable,Serializable
{
    private Calendar dataDeparture;
    private Calendar dataArrival;
    private String fromTown;
    private String townTo;
    private UUID jId;

    private ArrayList<Position> positions = null;

    public Journey(Journey journey)
    {
        this.positions = (ArrayList<Position>)journey.positions.clone();
        this.dataArrival = (Calendar) journey.dataArrival.clone();
        this.dataDeparture = (Calendar) journey.dataDeparture.clone();
        this.fromTown = journey.fromTown;
        this.townTo = journey.townTo;
        this.jId = UUID.randomUUID();
    }

    public Journey(Date dDeparture, Date dArrival, String dCity, String aCity, int posCount)
    {
        setDataDeparture(dDeparture);
        setDataArrival(dArrival);
        setDepartureCity(dCity);
        setArrivalCity(aCity);
        setPositionsCount(posCount);
        jId = UUID.randomUUID();
    }


    public void book(int position)
    {
        positions.get(position).setBooked (true);
    }

    public boolean isBooked(int numberPosition)
    {
        return positions.get(numberPosition -1).isBooked();
    }

    public int getPrise(int position)
    {
        int flyTime = dataArrival.get(Calendar.MINUTE) - dataDeparture.get(Calendar.MINUTE);
        return flyTime  + flyTime *( (position % 100)/100);
    }

    public Date getDataDeparture()
    {
        return dataDeparture.getTime();
    }

    public void setDataDeparture(Date dataDeparture)
    {
        if(this.dataDeparture == null){
            this.dataDeparture = new GregorianCalendar();
        }
        this.dataDeparture.setTime(dataDeparture);
    }

    public Date getDataArrival()
    {
        return dataArrival.getTime();
    }

    public void setDataArrival(Date dataArrival)
    {
        if(this.dataArrival == null) {
            this.dataArrival = new GregorianCalendar();
        }
        this.dataArrival.setTime(dataArrival);
    }

    public String getDepartureCity()
    {
        return fromTown;
    }

    public void setDepartureCity(String fromTown)
    {
        this.fromTown = fromTown;
    }

    public String getArrivalCity()
    {
        return townTo;
    }

    public void setArrivalCity(String townTo)
    {
        this.townTo = townTo;
    }

    public UUID getId()
    {
        return jId;
    }

    public Object clone()
    {
        return new Journey(this);
    }

    public void setId(UUID uuid)
    {
        jId = uuid;
    }


    public int getPositionCount()
    {
        return positions.size();
    }


    public void setPositionsCount(int positionsCount)
    {
        if(positions != null) {
            positions.clear();
        }
        positions = new ArrayList<>(positionsCount);

        for(int pos = 0; pos < positionsCount; pos++ ){
            positions.add(new Position(pos +1));
        }
    }

    public ArrayList<Position> getPositions()
    {
        return positions;
    }

    public void setPositions(int index, boolean booked)
    {
        if (positions == null || index >= positions.size() || index < 0)
            return;

        Position poss = new Position ( index + 1 );
        poss.setBooked ( booked );
        positions.set ( index, poss );
    }

    public void setPositions(ArrayList<Long> positions)
    {
        for (int i = 0; i < positions.size ( ); i++)
            setPositions ( i, positions.get(i) == 1);
    }
}


