package MainClasses;
import Menus.Menu;
import Menus.StartMenu;
import input_output.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Passenger implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;
    private String ssn;

    public Passenger(String name, String email, String phoneNumber, String id){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ssn = id;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.ssn = id;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return ssn;
    }
    public static Passenger addPassenger(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter name : ");
        String name = input.nextLine();
        System.out.println("Enter Email : ");
        String Email = input.next();
        System.out.println("Enter Phone : ");
        String phone = input.next();
        System.out.println("Enter  ssn : ");
        String Ssn = input.next();
        return new Passenger(name,Email,phone,Ssn);
    }
    public static void removePassenger(Booking booking) throws IOException {
        Passenger passenger = selectPassenger(booking.getPassengers());
        booking.getPassengers().remove(passenger);
        for (Ticket ticket : booking.getTickets()){
            if (ticket.getPassenger() == passenger){
                Seat.removeSeat(ticket.getSeat());
                booking.getTickets().remove(ticket);
                Input.tickets.remove(ticket);
                System.out.println("removed successfully ! ");
                break;
            }
        }
        int totalPaymentAmount = 0;
        for (Ticket t :booking.getTickets()){
            totalPaymentAmount+= Payment.calcPayAmount(t);
        }
        booking.setPayment(new Payment(totalPaymentAmount,booking.getPayment().getPayment_method()));
    }
    public static Passenger selectPassenger(ArrayList<Passenger>passengers) throws IOException {
        for (Passenger passenger : passengers){
            System.out.println("passenger ssn "  + passenger.getId() +  " passenger name : "+ passenger.getName());
        }
        System.out.println("Enter passenger ssn you want to select : ");
        Scanner input = new Scanner(System.in);
        Passenger wantedPassenger  = null;
        try {
            String index = input.nextLine();
                for (Passenger passenger : passengers){
                    if ( passenger.getId().equals(index)){
                        wantedPassenger = passenger;
                    }
                }
        }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
            System.out.println("Invalid name Please try again!");
            selectPassenger(passengers);
        }
        if (wantedPassenger == null){
            System.out.println("Invalid code Please try again!");
            selectPassenger(passengers);
        }
        return wantedPassenger;
    }

}
