package sample;

public class CitySpecification implements Specification<Journey>
{
    private String city;
    private boolean isDepartureCity;

    public CitySpecification(String city, boolean isDepartureCity)
    {
        this.city = city;
        this.isDepartureCity = isDepartureCity;
    }

    @Override
    public boolean isSatisfied(Journey item)
    {
        String citySearch = isDepartureCity ? item.getDepartureCity() : item.getArrivalCity();
        return city.equals(citySearch);
    }
}
