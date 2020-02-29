package sample;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GUIHelper
{
    public static final int idLenght = 5;
    public static final String numberRegex = "0123456789";
    public static final String dateFormat = "dd/MM/yyyy hh:mm";

    public enum ErrorType {None,DatePassedError,InvalidOrder,CityNotExist};

    public static ErrorType checkDatum(Date dep, Date arr, String cityDep, String cityArr)
    {
        if(!CityDatabase.getInstance().isCityExist(cityDep) || !CityDatabase.getInstance().isCityExist(cityArr) ){
          return ErrorType.CityNotExist;
        }
        if(!arr.after(dep)){
          return ErrorType.InvalidOrder;
        }

        Date now = new Date();
        if(dep.before(now) || arr.before(now)){
            return ErrorType.DatePassedError;
        }
        return ErrorType.None;
    }


    public static Date stringToDate(String date){
        try {
            return new SimpleDateFormat (dateFormat).parse(date);
        }catch (ParseException e){
            return null;
        }
    }

    public static String dateToString(Date date)
    {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static Alert createAlert(Alert.AlertType alertType,
                                    String title, String headerText,
                                    String contentText, ButtonType... buttonTypes)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(buttonTypes);

        return alert;
    }
    public static Alert createAlert(ErrorType errorType){
        return createAlert(Alert.AlertType.ERROR,"ERROR","Error while adding",
                errorToString(errorType),ButtonType.OK);
    }

    public static String errorToString(ErrorType errorType)
    {
        switch (errorType){
            case None:              return "None";
            case CityNotExist:      return "City not exist";
            case InvalidOrder:      return "Invalid date";
            case DatePassedError:   return "Date already passed";
            default:                return "";
        }
    }

}



