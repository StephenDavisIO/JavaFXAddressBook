package Application;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

import static Application.Constants.BIRTH_YEAR_START;

public class Helpers {
    public static Integer[] generateDays(){
        Integer[] list = new Integer[31];
        for(int i = 1; i < 32; i++)
            list[i - 1] = i;
        return list;
    }

    public static String[] generateMonths(){
        return new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    public static Integer[] generateYears(){
        int year = LocalDate.now().getYear();
        int size = year - BIRTH_YEAR_START + 1;
        Integer[] list = new Integer[size];
        for(int i = 0; i < size; i++){
            list[i] = year;
            year--;
        }
        return list;
    }

    public static int getMonthNumber(String month){
        switch (month.toLowerCase()){
            case "jan":
            case "january":
                return 1;
            case "feb":
            case "february":
                return 2;
            case "mar":
            case "march":
                return 3;
            case "apr":
            case "april":
                return 4;
            case "may":
                return 5;
            case "jun":
            case "june":
                return 6;
            case "jul":
            case "july":
                return 7;
            case "aug":
            case "august":
                return 8;
            case "sep":
            case "september":
                return 9;
            case "oct":
            case "october":
                return 10;
            case "nov":
            case "november":
                return 11;
            case "dec":
            case "december":
                return 12;
            default:
                return 0;
        }
    }
}
