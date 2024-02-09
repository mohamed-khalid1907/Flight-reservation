package MainClasses;
import Menus.UserMenu;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Menus.*;

public class Seat implements Serializable {
    private final int seatNumber;
    private String seatClass;
    private boolean available;
    public Seat(int seatNumber, String seatClass){
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.available = true;
    }
    // Setters
    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    // Getters
    public int getSeatNumber(){
        return this.seatNumber;
    }
    public String getSeatClass(){
        return this.seatClass;
    }
    public boolean isAvailable(){
        return this.available;
    }
    public static void createSeats(int number, ArrayList<Seat> seats, String seatClass){
        int size =seats.size();
        for (int i = size; i < number + size; i++) {
            Seat seat = new Seat(i, seatClass);
            seats.add(seat);
        }
    }
    public static String displayAllSeats(ArrayList<Seat> seats){
        int firstClassNumber = 0;
        int businessClassNumber = 0;
        int economyClassNumber = 0;
        for (Seat seat:
                seats) {
            if (seat.getSeatClass().equalsIgnoreCase("first class")&& seat.available){
                firstClassNumber++;
            } else if (seat.getSeatClass().equalsIgnoreCase("business class") && seat.available) {
                businessClassNumber++;
            }else {
                if (seat.available) {
                    economyClassNumber++;
                }
            }
        }
        return ("1)available First Class Seats x" + firstClassNumber +"\n2)available Business Class Seats x" + businessClassNumber +
                "\n3)available Economy Class Seats x" + economyClassNumber);
    }
    public static Seat selectSeat(ArrayList<Seat> seats ) throws IOException {
        Scanner input = new Scanner(System.in);
        displayAllSeats(seats);
        System.out.println("Enter the index of class you want : (Enter -1 to go back )");
        Seat  seat = null;
        try {
            int index = Integer.parseInt(input.next());
            if (index == -1) {
                UserMenu.mainMenu();
            }
            else {
                switch (index){
                    case 1 :
                        seat = selectClass("First Class",seats);
                        return seat;
                    case 2 :
                        seat = selectClass("Business Class",seats);
                        return seat;
                    case 3 :
                        seat = selectClass("Economy Class",seats);
                        return seat;
                    default:
                        System.out.println("Enter valid option");
                        selectSeat(seats);
                }
            }
        }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
            System.out.println("Invalid Flight Please try again!");
        }
        return seat;
    }
    private static Seat selectClass(String type , ArrayList<Seat> seats){
        Seat temp = null;
        for (Seat seat : seats){
            if (seat.getSeatClass().equalsIgnoreCase(type)&& seat.available){
                seat.available=false;
                temp  = seat;
                break;
            }
        }
        return temp;
    }
    public static void removeSeat(Seat seat){
        seat.available=true;
    }

}