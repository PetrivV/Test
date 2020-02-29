package sample;

public class Position
{
    private int numberPosition;
    private boolean booked;
    public Position(int numberPosition){
        this.numberPosition = numberPosition;
        booked = false;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked)
    {
        this.booked = booked;
    }

    public int getNumberPosition()
    {
        return numberPosition;
    }

    @Override
    public String toString() {
        return (booked ? "1" : "0");
    }

}
