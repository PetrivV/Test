package sample;

import java.util.Date;

public class DateSpecification implements Specification<Journey>
{
    private Date date;
    private boolean isDeparture;

    public DateSpecification(Date date, boolean isDeparture)
    {
        this.date = date;
        this.isDeparture = isDeparture;
    }

    @Override
    public boolean isSatisfied(Journey item)
    {
        Date localDateTime = isDeparture ? item.getDataDeparture() : item.getDataArrival();

        if(localDateTime.getMonth() != date.getMonth())
            return false;
        if (localDateTime.getDay() != date.getDay())
            return false;

        return true;
    }
}
