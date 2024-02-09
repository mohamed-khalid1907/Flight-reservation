package MainClasses;
import Menus.AdminMenu;

import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

import static input_output.Input.airports;
import static input_output.Input.flights;

public class Airport implements Serializable {
    private int airportCode;
    private String airportName;
    private String location;

    // Constructor
    public Airport(int airportCode, String airportName, String location) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.location = location;
    }

    // Getter methods
    public int getAirportCode() {
        return airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getLocation() {
        return location;
    }

    public void setAirportCode(int airportCode) {
        this.airportCode = airportCode;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void displayAirport(){
        System.out.println("Airport Code: " + airportCode + " Airport Name: " + airportName + " Airport Location: " +
                location);
    }
    public static void addAirport(){
        int code =generateAirportCode();
        String loc , name ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter airport location :");
        loc = scanner.next();
        System.out.println("Enter airport name :");
        name = scanner.next();
        airports.add(new Airport(code,name,loc));
        System.out.println("Airport added successfully!");
    }
    public static void removeAirport() throws IOException {
        displayAirports();
        System.out.println("Enter the Airport you want to remove : ");
        Airport air = getAirport();
        airports.remove(air);
        if ((air)!=null){
            System.out.println("Airport removed Successfully !");
        }
    }
    public static void displayAirports() throws IOException {
        try {
            for (Airport airport : airports) {
                airport.displayAirport();
            }
        }catch (NullPointerException e){
            System.out.println("No airports are valid please add airports !");
            AdminMenu.mainMenu();
        }
    }
public static Airport getAirport(){
    Scanner input = new Scanner(System.in);
    Airport air = null;
    try {
        int airportCode = Integer.parseInt(input.next());
        if (airportCode == -1){
            return null;
        }
        for (Airport airports : airports) {
            if (airports.getAirportCode() == airportCode) {
                air = airports;
                return air;
            }
        }
    }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
        System.out.println("Error Airport Does not Exist!");
        getAirport();
            return air;
    }
    if (air == null){
        System.out.println("Error Airport Does not Exist Enter the Code Again: ");
        return null;
    }
    return air;
}
    public static int generateAirportCode(){
        int count = 0 ;
        for (Airport airport : airports){
            if (airport.airportCode>count){
                count=airport.airportCode;
            }
        }
        return (count+1);
    }
}
