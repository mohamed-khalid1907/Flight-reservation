package MainClasses;
import java.io.IOException;
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Menus.AdminMenu;
import Menus.Menu;
import Menus.UserMenu;
import input_output.*;
import static input_output.Input.airports;
import static input_output.Input.flights;
public class Flight implements Serializable {
    static Scanner input = new Scanner(System.in);
    private final int flightNumber;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private final Date departureTime;
    private final Date arrivalTime;
    private int availableSeats;
    private ArrayList<Seat> seats = new ArrayList<Seat>();

    public Flight(int flightNumber, Airport departureAirport, Airport arrivalAirport, Date departureTime,
                  Date arrivalTime, int availableSeats,ArrayList<Seat> seats) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.seats = seats;
    }
    public int getFlightNumber() {
        return flightNumber;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void displayFlight(){
        System.out.println("Flight number: "+ String.format("%06d",flightNumber) +
                " Departure Airport: " + departureAirport.getAirportName() +
                " Arrival Airport: " + arrivalAirport.getAirportName() + " Departure Time: "
                + departureTime.returnDate() + " Arrival Time: " + arrivalTime.returnDate() + " Available Seats: "
                + availableSeats + "\nSeats: \n" + Seat.displayAllSeats(seats));
    }

    public static void EditFlight() throws IOException {
        int index = selectFlight();
        if (index ==-1) {
            return;
        }
        Flight flight = Input.flights.get(index);
        System.out.println("Enter What You Want to Edit(1-Departure Airport 2-Arrival Airport 3-Departure and arrival" +
                " Time" +
                " 4-Seats 5-Go Back): ");
        int choice = Integer.parseInt(input.next());
        try {

            switch(choice){
                case 1:
                    flight.EditDepartureAirport();
                    break;
                case 2:
                    flight.EditArrivalAirport();
                    break;
                case 3:
                    flight.EditTime();
                    break;
                case 4:
                    flight.EditSeats();
                    break;
                case 5:
                    return; //Exits From the Function

                default:
                    System.out.println("Invalid Input try again!");
                    AdminMenu.mainMenu();
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            AdminMenu.mainMenu();
        }
    }


    public static void AddFlight() throws IOException {
        int flightNumber = generateFlightNumber();
        Airport.displayAirports();
        System.out.println("Enter The Departure Airport Code: ");
        Airport departureAirport = Airport.getAirport();
        if (departureAirport == null){
           return;
        }
        System.out.println("Enter The Arrival Airport Code: ");
        Airport arrivalAirport = Airport.getAirport();
        if (arrivalAirport == null){
            return;
        }
        while (arrivalAirport == departureAirport){
            System.out.println("You can't Travel to The same Airport");
            arrivalAirport = Airport.getAirport();
        }
        Date departureDate = getDate("Departure");
        int[] time = getTime();
        Date arrivalDate = Date.addDate(time[0],time[1], departureDate);
        checkDate(departureDate, arrivalDate);
        int seatsNumber = getSeatsNumber();
        ArrayList<Seat> seats = new ArrayList<Seat>();
        setSeats(seatsNumber, seats);
        Flight flight = new Flight(flightNumber,departureAirport,arrivalAirport,departureDate
                ,arrivalDate,seatsNumber, seats);
        flights.add(flight);
    }

    public static int generateFlightNumber(){
       int count = 0 ;
       for (Flight flight : flights){
           if (flight.flightNumber>count){
               count=flight.flightNumber;
           }
       }
       return (count+1);
    }

    public static int[] getTime(){
        int[] time = new int[2];
        while (true){
            try {
                System.out.println("Enter Duration of travel (in format hh:mm): ");
                String flightLength = input.next();
                time[0] = Integer.parseInt(flightLength.substring(0,2));
                time[1] = Integer.parseInt(flightLength.substring(3,5));
                return time;
            }catch (InputMismatchException | NumberFormatException |StringIndexOutOfBoundsException exp){
                System.out.println("Invalid Input Make Sure You Type It in hh:mm Format!");
            }
        }
    }


    public static int getSeatsNumber(){
        System.out.println("Enter The Number of Seats(Minimum is 100 and Maximum is 200): ");
        while (true) {
            try {
                int seats = Integer.parseInt(input.next());
                if(seats < 100 || seats > 200){
                    throw new InputMismatchException();
                }
                return seats;
            } catch (InputMismatchException | NumberFormatException exp) {
                System.out.println("Error Invalid Number of Seats Enter the Number of Seats Again: ");
            }
        }
    }

    public static void setSeats(int seatNumber, ArrayList<Seat> seats){
        boolean failed = true;
        while (failed){
            try {
                System.out.println("Enter How Many First class Seats: ");
                int firstClass = Integer.parseInt(input.next());
                System.out.println("Enter How Many Business class Seats: ");
                int businessClass = Integer.parseInt(input.next());
                System.out.println("Enter How Many Economy class Seats: ");
                int economyClass = Integer.parseInt(input.next());
                if (firstClass + economyClass + businessClass != seatNumber){
                    System.out.println("Error the Total Number of Seats Doesn't match Try Again!");
                    break;
                }
                seats.clear();
                Seat.createSeats(firstClass, seats, "First Class");
                Seat.createSeats(businessClass, seats, "Business Class");
                Seat.createSeats(economyClass, seats, "Economy Class");
                failed = false;
            }catch (InputMismatchException | NumberFormatException exp) {
                System.out.println("Error Invalid Number Please Try Again");
            }

        }
    }

    public static int getYear(String type){
        System.out.println("Enter The " + type +" Year: ");
        while (true) {
            try {
                int year = Integer.parseInt(input.next());
                if(year < Year.now().getValue()){
                    throw new InputMismatchException();
                }
                return year;
            } catch (InputMismatchException | NumberFormatException exp) {
                System.out.println("Error Invalid Year Enter the Year Again: ");
            }
        }
    }

    public static int getMonth(String type){
        System.out.println("Enter The " + type +" Month(1 to 12): ");
        while (true){
            try {
                int month = Integer.parseInt(input.next());
                if (month < 1 || month > 12){
                    throw new InputMismatchException();
                }
                return month;
            }catch (InputMismatchException | NumberFormatException exp){
                System.out.println("Error Invalid Month Enter the Month Again: ");
            }
        }
    }

    public static int getDay(String type){
        System.out.println("Enter The " + type +" Day(1 to 31): ");
        while (true){
            try {
                int day = Integer.parseInt(input.next());
                if (day < 1 || day > 31){
                    throw new InputMismatchException();
                }
                return day;
            }catch (InputMismatchException | NumberFormatException exp){
                System.out.println("Error Invalid Day Enter the Day Again: ");
            }
        }
    }

    public static int getHour(String type){
        System.out.println("Enter The " + type +" Hour(0 to 24): ");
        while (true){
            try {
                int hour = Integer.parseInt(input.next());
                if (hour < 0 || hour > 24){
                    throw new InputMismatchException();
                }
                return hour;
            }catch (InputMismatchException | NumberFormatException exp){
                System.out.println("Error Invalid Hour Enter the Hour Again: ");
            }
        }
    }

    public static int getMinute(String type){
        System.out.println("Enter The " + type +" Minute(0 to 59): ");
        while (true){
            try {
                int minute = Integer.parseInt(input.next());
                if (minute < 0 || minute > 59){
                    throw new InputMismatchException();
                }
                return minute;
            }catch (InputMismatchException | NumberFormatException exp){
                System.out.println("Error Invalid Minute Enter the Minute Again: ");
            }
        }
    }

    public static Date getDate(String type){
        return new Date(getYear(type),getMonth(type),getDay(type),getHour(type),getMinute(type));
    }

    public static void checkDate(Date departureDate, Date arrivalDate){
        while (departureDate.getYear() > arrivalDate.getYear()){
            errorDate(departureDate,arrivalDate);
        }if (departureDate.getYear() == arrivalDate.getYear()){
            while (departureDate.getMonth() > arrivalDate.getMonth()){
                errorDate(departureDate,arrivalDate);
            }
            if (departureDate.getMonth() == arrivalDate.getMonth() ){
                while (arrivalDate.getDay() < departureDate.getDay()){
                    errorDate(departureDate,arrivalDate);
                }
                if (arrivalDate.getDay() == departureDate.getDay()){
                    while (departureDate.getHour() >= arrivalDate.getHour()){
                        errorDate(departureDate,arrivalDate);
                    }
                }
            }
        }
    }

    public static void errorDate(Date departureDate, Date arrivalDate){
        System.out.println("Error in Date Please Try Again");
        departureDate = getDate("Departure");
        arrivalDate = getDate("Arrival");
    }

    public static void ShowSchedule() throws IOException {
        boolean existFlights = false;
        int counter = 1;
        try {
            for (Flight flight :flights) {
                System.out.print(counter +"- ");
                flight.displayFlight();
                existFlights = true;
                counter++;
            }
        }
        catch (NullPointerException ignored){}
        finally {
            if (!existFlights){
                System.out.println("No Available Flights!");
                if (Menu.activeUser.getPassword().equals("adimn")){
                    AdminMenu.mainMenu();
                }
                else {
                    UserMenu.mainMenu();
                }
            }
        }
    }
    public void EditSeats(){
        setSeats(EditAvailableSeats(),seats);
    }
    public void EditDepartureAirport(){
        System.out.println("Enter new Departure Airport Code: (-1 to go back)");
        this.departureAirport = Airport.getAirport();
    }
    public void EditArrivalAirport() {
        System.out.println("Enter new Arrival Airport Code: (-1 to go back)");
        this.arrivalAirport = Airport.getAirport();
    }

    public void EditTime() {
        Date departureDate = getDate("Departure");
        int[] time = getTime();
        Date arrivalDate = Date.addDate(time[0],time[1], departureDate);
        checkDate(departureDate, arrivalDate);
        this.departureTime.editDate(departureDate);
        this.arrivalTime.editDate(arrivalDate);
    }
    public int EditAvailableSeats() {
        int number = getSeatsNumber();
        this.availableSeats = number;
        return number;
    }


    public static void removeFlight() throws IOException {
    int index = selectFlight();
    if (index ==-1) return;
    flights.remove(index);
    System.out.println("removed Successfully !");
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }
    public static int selectFlight() throws IOException {
        try {
            System.out.println("1-Search Flights\n2-All Flights");
            int choice = Integer.parseInt(input.next());
            switch (choice){
                case 1:
                    System.out.println("Enter Departure City: ");
                    String departureCity = input.next();
                    System.out.println("Enter Arrival City: ");
                    String arrivalCity = input.next();
                    ArrayList<Integer> indexes = SearchFlight(departureCity,arrivalCity);
                    System.out.println("Select the flight you want: (-1 to go back)");
                    int index = Integer.parseInt(input.next());
                    if (index == -1) return -1;
                    int realIndex = indexes.get(index - 1);
                    flights.get(realIndex - 1);
                    return (realIndex - 1);
                case 2:
                    ShowSchedule();
                    System.out.println("Select the flight you want: (-1 to go back)");
                    int index2 = Integer.parseInt(input.next());
                    if (index2 == -1) return -1;
                    flights.get(index2 - 1);
                    return index2 - 1;
                default:
                    System.out.println("Invalid Input!");
                    return -1;
            }
        }catch (NullPointerException | InputMismatchException | NumberFormatException | IndexOutOfBoundsException exp){
            System.out.println("Invalid Flight Please try again!");
            return  -1;
        }
    }
    public static ArrayList<Integer> SearchFlight(String departureCity, String arrivalCity){
        boolean existFlights = false;
        ArrayList<Integer> index = new ArrayList<Integer>();
        int counter = 1;
        try {
            for (Flight flight :flights) {
                if(departureCity.equalsIgnoreCase(flight.getDepartureAirport().getLocation()) &&
                        arrivalCity.equalsIgnoreCase(flight.getArrivalAirport().getLocation())){
                    System.out.print(counter +"- ");
                    flight.displayFlight();
                    existFlights = true;
                    index.add(flight.getFlightNumber());
                    counter++;
                }
            }
            return index;
        }
        catch (NullPointerException ignored){
            return null;
        }
        finally {
            if (!existFlights){
                System.out.println("No Available Flights Make Sure You Type the Cities Right Or Wait for Admins to Add" +
                        " Flights.");
            }
        }
    }


}

