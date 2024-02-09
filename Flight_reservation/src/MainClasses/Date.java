package MainClasses;
import input_output.*;

import java.io.Serializable;

public class Date implements Serializable {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String returnDate() {
        return String.format(year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day) + " Time: "
                + String.format("%02d", hour) + ":" + String.format("%02d", minute) );
    }
    public void editDate(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }
    public void editDate(Date date) {
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
        this.hour = date.getHour();
        this.minute = date.getMinute();
    }
    public static Date addDate(int hour,int minute, Date date){
        int totalMinutes = date.minute + minute;
        int newMinute = totalMinutes % 60;
        int totalHour = date.hour + totalMinutes / 60 + hour;
        int newHour = totalHour % 24;
        int totalDay = date.day + totalHour / 24;
        int newDay = totalDay % 31;
        if (newDay == 0) newDay++;
        int totalMonth = date.month + totalDay / 31;
        int newMonth = totalMonth % 12;
        if (newMonth == 0) newMonth++;
        int newYear = date.year + totalMonth / 12;

        return new Date(newYear,newMonth,newDay,newHour,newMinute);
    }
}
