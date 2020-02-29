package sample;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CommonScreen extends Observer<AirportManager>
{

    protected Stage stage = null;
    protected Scene scene = null;


    protected CommonScreen(){
        super(AirportManager.getInstance());
        stage = new Stage();
    }

    protected void initScene(Parent parent, double width, double height)
    {
        scene = new Scene(parent, width, height);


        stage.setScene (scene);
        stage.setResizable(false);
        stage.initModality( Modality.APPLICATION_MODAL);
    }

    public void show()
    {
        stage.show();
    }
    public void close(){
        stage.close();
    }

    @Override
    public void update(Object j, Action a) {}
}
