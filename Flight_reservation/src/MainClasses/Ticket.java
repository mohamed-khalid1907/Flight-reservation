package MainClasses;

import input_output.Input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static input_output.Input.flights;
import static input_output.Input.tickets;

public class Ticket implements Serializable {
    private final int ticketNumber;
    private int fare;
    private Boolean ticketStatus;
    private Passenger passenger;
    private Seat seat;

    public Ticket(int ticketNumber, int fare, Boolean ticketStatus, Passenger passenger, Seat seat) {
        this.ticketNumber = ticketNumber;
        this.fare = fare;
        this.ticketStatus = ticketStatus;
        this.passenger = passenger;
        this.seat =seat;
    }
    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public Boolean getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public static Ticket addTicket(Seat seat, Passenger passenger){
        int fare;
        if (seat.getSeatClass().equalsIgnoreCase("first class")){
            fare = 10000;
        } else if (seat.getSeatClass().equalsIgnoreCase("business class")) {
            fare = 5000;
        } else  fare =2000;
        Ticket ticket = new Ticket(generateTicketNumber(),fare,true, passenger,seat);
        Input.tickets.add(ticket);
        return ticket;
    }
    private static int generateTicketNumber(){
        int count = 0 ;
        for (Ticket ticket : tickets){
            if (ticket.getTicketNumber()>count){
                count=ticket.getTicketNumber();
            }
        }
        return (count+1);
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

}

