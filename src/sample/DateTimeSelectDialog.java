package sample;

import javafx.scene.control.Dialog;

public class DateTimeSelectDialog extends Dialog
{
    private DateTimePicker picker = null;

    public DateTimeSelectDialog()
    {
        super();
        picker = new DateTimePicker();
        super.getDialogPane().setContent(picker);
    }
    public String getDateTime () {
        return picker.toString();
    }
}
