package sample;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import org.w3c.dom.Text;

public class PositionLabel extends Label {

    private Position position = null;

    public PositionLabel(Position position) {

        super(String.valueOf(position.getNumberPosition()));
        this.position = position;

        if (position.isBooked()) {
           super.setTextFill(Color.RED);
        }

    }

    public Position getPosition() {

        return position;
    }
}
