package Classes;

import static Application.Constants.CSV_NULL;

public class MyDate extends Contact {
    private int day;
    private int month;
    private int year;

    public MyDate(){}
    public MyDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }
    public String getMonthShortForm(){
        switch(month){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return CSV_NULL;
        }
    }
    public String getMonthLongForm(){
        switch(month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return CSV_NULL;
        }
    }
    public void setMonth(int month){
        if(month >= 1 && month <= 12)
            this.month = month;
        else
            this.month = 0;
    }
    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }

    @Override
    public String toString(){
        return year + " " + getMonthLongForm() + " " + day;
    }
}
