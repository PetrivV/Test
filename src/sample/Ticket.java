package sample;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.UUID;

public class Ticket
{
    private int position;
    private double prise;
    private UUID id;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getPrise() {
        return prise;
    }

    public void setPrise(double prise) {
        this.prise = prise;
    }

    public Ticket(int position,double prise, UUID id)
    {
        this.position = position;
        this.prise = prise;
        this.id = id;
    }

    @Override
    public String toString()
    {
        return position + " " + prise + " " + id.toString();
    }

    public static class CommonScreen
    {
        protected Stage stage;
        protected Scene scene;

        protected CommonScreen()
        {
        }

        protected void init(Parent parent)
        {
            stage = new Stage();

        }

        public void show()
        {
            stage.show();
        }

    }
}
