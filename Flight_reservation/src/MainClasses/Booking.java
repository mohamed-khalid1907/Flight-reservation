package MainClasses;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Menus.AdminMenu;
import Menus.Menu;
import Menus.StartMenu;
import Menus.UserMenu;
import com.sun.source.tree.NewArrayTree;
import input_output.*;
public class Booking implements Serializable {

    private int bookingId;
    private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
    private Flight flight;
    private ArrayList<Ticket> tickets= new ArrayList<Ticket>();
    private ArrayList<Seat> seats= new ArrayList<Seat>();
    private Payment payment;
    private Boolean bookingStatus;

    public Booking(int bookingId, ArrayList<Passenger> passengers, Flight flight, ArrayList<Ticket> tickets, ArrayList<Seat> seats, Payment payment){
        this.bookingId = bookingId;
        this.passengers = passengers;
        this.flight = flight;
        this.tickets = tickets;
        this.seats = seats;
        this.payment = payment;
        this.bookingStatus = false;
        Input.bookings.add(this);
    }
    public Booking(){
        this.bookingStatus = false;
        Input.bookings.add(this);
    }
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean getbookingStatus() {
        return bookingStatus;
    }

    public void setbookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public static void addBooking() throws IOException {
        int indexFlight = Flight.selectFlight();
        if (indexFlight == -1){
            UserMenu.mainMenu();
            return;
        }
        Booking newBooking = new Booking();
        newBooking.bookingId = generateBookingId();
        newBooking.flight=Input.flights.get(indexFlight);
        int numOfPassengers = 0;
        System.out.println("Enter number of passengers you want : ( Enter -1 to go back)");
        Scanner input = new Scanner(System.in);
        try {
            numOfPassengers = Integer.parseInt(input.next());
            if (numOfPassengers <=0) {
                if (numOfPassengers != -1){
                    System.out.println("Error: Enter valid number");
                }
                addBooking();
            }
            
        }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
            System.out.println("Invalid Flight Please try again!");
            addBooking();
        }
        for ( int counter = 0 ; counter < numOfPassengers ; counter++){
            System.out.println("~ Passenger "+(counter+1)+" ~");
            newBooking.passengers.add(Passenger.addPassenger());
            Seat seat = Seat.selectSeat(newBooking.flight.getSeats());
            while (true){
                if (seat == null ){
                    seat = Seat.selectSeat(newBooking.flight.getSeats());
                }
                else {
                    break;
                }
            }
            newBooking.seats.add(seat);
            newBooking.tickets.add(Ticket.addTicket(newBooking.seats.get(counter),newBooking.passengers.get(counter)));

        }
        int totalPaymentAmount = 0;
        for (Ticket t : newBooking.tickets){
            totalPaymentAmount+= Payment.calcPayAmount(t);
        }
        String paymentMethod=Payment.chooseMethod();
        newBooking.payment =new Payment(totalPaymentAmount,paymentMethod);

        Input.bookings.add(newBooking);
        Menu.activeUser.userBookings.add(newBooking);
        confirmBooking(newBooking);
    }
    private static int generateBookingId(){
        int count = 0 ;
        for (Booking booking : Input.bookings){
            if (booking.bookingId>count){
                count = booking.bookingId;
            }
        }
        return count+1;
    }
    public static void displayUserBookings() throws IOException {
        boolean exist = false;
        try {
            Scanner input = new Scanner(System.in);
            for (Booking booking : Menu.activeUser.userBookings){
                System.out.println("\t\t\t----------------------------------------------------------");
                System.out.println("booking id : "+booking.bookingId);
                booking.flight.displayFlight();
                System.out.println("\t\t~ Tickets information ~\n");
                for (Ticket ticket : booking.tickets){
                    System.out.println("Ticket Number : "+ticket.getTicketNumber() +"\nTicket fare : "+ ticket.getFare()+"\npassenger ID: "+ ticket.getPassenger().getId()
                            + "\nPassenger Name : "+ticket.getPassenger().getName()+ "\nSeat number:"+ticket.getSeat().getSeatNumber());
                }
                System.out.println("amount of payment : "+booking.payment.getPayment_amount());
                System.out.println("\t\t\t----------------------------------------------------------");
                exist=true;
            }
            if (!exist){
                throw new NullPointerException() ;
            }
            System.out.println("press any button to exit : ");
            switch (input.next()){
                default :
                    UserMenu.mainMenu();
                    break;
            }
        }catch (NullPointerException e){
            if (!exist){
                System.out.println("No Bookings Found!");
                UserMenu.mainMenu();
            }
        }

    }
    public static void displayBookings() throws IOException {
        boolean exist = false;
        try {
            for (Booking booking : Menu.activeUser.userBookings){
                System.out.println("\t\t\t----------------------------------------------------------");
                System.out.println("booking id : "+booking.bookingId);
                booking.flight.displayFlight();
                System.out.println("\t\t~ Tickets information ~\n");
                for (Ticket ticket : booking.tickets){
                    System.out.println("Ticket Number : "+ticket.getTicketNumber() +"\nTicket fare : "+ ticket.getFare()+"\npassenger ID: "+ ticket.getPassenger().getId()
                            + "\nPassenger Name : "+ticket.getPassenger().getName()+ "\nSeat number:"+ticket.getSeat().getSeatNumber());
                }
                System.out.println("amount of payment : "+booking.payment.getPayment_amount());
                System.out.println("\t\t\t----------------------------------------------------------");
                exist=true;
            }
            if (!exist){
                throw new NullPointerException() ;
            }
        }catch (NullPointerException e){
            if (!exist){
                System.out.println("No Bookings Found!");
                UserMenu.mainMenu();
            }
        }

    }
    public static void removeBooking() throws IOException {
        displayBookings();
        System.out.println("choose Booking id of booking you want to remove : ( Enter -1 to go back )");
        Booking wantedBooking  = selectBooking();
        if (wantedBooking == null){
            System.out.println("Invalid code Please try again!");
            removeBooking();
        }
        else {
            for (Ticket ticket : wantedBooking.tickets){
                ticket.getSeat().setAvailable(true);
                Input.tickets.remove(ticket);
            }
            Input.bookings.remove(wantedBooking);
            Menu.activeUser.userBookings.remove(wantedBooking);
            System.out.println("removes successfully");
            UserMenu.mainMenu();
        }
        }
        public static void modifyBooking() throws IOException {
            displayBookings();
            System.out.println("choose Booking id of booking you want to modify : ( Enter -1 to go back )");
            Booking wantedBooking  = selectBooking();
            while (true){
                if (wantedBooking == null){
                    wantedBooking  = selectBooking();
                }
                else {
                    break;
                }
            }
            System.out.println("our policies make edit valid on : \n 1) Change Flight \n" +
                    "2) add passenger \n3) remove passenger");
            System.out.println("----------------------------------------------------------");
            System.out.println("Enter what you want to edit (Enter -1 to go back ) :");
            Scanner input = new Scanner(System.in);
            try {
                int index = Integer.parseInt(input.next());
                if (index == -1) {
                    UserMenu.mainMenu();
                }
                else {
                    switch (index){
                        case 1:
                            editBookingFlight(wantedBooking);
                            break;
                        case 2:
                            addTicket(wantedBooking);
                            break;
                        case 3:
                            Passenger.removePassenger(wantedBooking);
                            break;
                        default:
                            System.out.println("not valid option try again !");
                            modifyBooking();
                            break;
                    }
                }
            }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
                System.out.println("Invalid option Please try again!");
                modifyBooking();
            }
            System.out.println("press any button to exit : ");
            switch (input.next()){
                default :
                    UserMenu.mainMenu();
            }
        }
    public static void modifyBooking(Booking booking) throws IOException {
        System.out.println("our policies make edit valid on : \n 1) Change Flight \n" +
                "2) add passenger \n3) remove passenger");
        System.out.println("----------------------------------------------------------");
        System.out.println("Enter what you want to edit (Enter -1 to go back ) :");
        Scanner input = new Scanner(System.in);
        try {
            int index = Integer.parseInt(input.next());
            if (index == -1) {
                UserMenu.mainMenu();
            }
            else {
                switch (index){
                    case 1:
                        editBookingFlight(booking);
                        break;
                    case 2:
                        addTicket(booking);
                        break;
                    case 3:
                        Passenger.removePassenger(booking);
                        break;
                    default:
                        System.out.println("not valid option try again !");
                        modifyBooking();

                }
            }
        }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
            System.out.println("Invalid option Please try again!");
            modifyBooking(booking);
        }
        System.out.println("press any button to exit : ");
        switch (input.next()){
            default :
                UserMenu.mainMenu();
        }
    }
        public static Booking selectBooking() throws IOException {
        Scanner input = new Scanner(System.in);
            Booking wantedBooking  = null;
            boolean  exist = false ;
            try {
                int index = Integer.parseInt(input.next());
                if (index == -1) {
                   UserMenu.mainMenu();
                }
                else {
                    for (Booking booking : Menu.activeUser.userBookings){

                        if (booking.bookingId==index){
                            wantedBooking = booking;
                            exist = true ;
                        }
                    }
                }
            }catch (NullPointerException | InputMismatchException | NumberFormatException exp){
                System.out.println("Invalid code Please try again!");
            }
            finally {
                if (!exist){
                    System.out.println("Invalid code Please try again!");
                }
            }
            return wantedBooking;
        }
        public static void editBookingFlight(Booking booking) throws IOException {
            for (Ticket ticket : booking.tickets){
                ticket.getSeat().setAvailable(true);
                Input.tickets.remove(ticket);
            }
        Input.bookings.remove(booking);
        Menu.activeUser.userBookings.remove(booking);
        addBooking();
        }
        public static void confirmBooking(Booking booking) throws IOException {
            System.out.println("This is your booking details :  ");
            Scanner input = new Scanner(System.in);
            System.out.print("booking id : "+booking.bookingId+"\n");
            System.out.println("~ flight information ~");
            booking.flight.displayFlight();
            System.out.println("\t\t~ Tickets information ~\n");
            int i = 1;
            for (Ticket ticket : booking.tickets){
                System.out.println(i+" - Ticket Number : "+ticket.getTicketNumber() +"\nTicket fare : "+ ticket.getFare()+"\npassenger ID: "+ ticket.getPassenger().getId()
                        + "\nPassenger Name : "+ticket.getPassenger().getName()+ "\nSeat number "+ticket.getSeat().getSeatNumber()+ "\n Seat Class : "+ticket.getSeat().getSeatClass() );
                i++;
            }
            System.out.println("amount of payment for this booking : "+booking.payment.getPayment_amount());
            System.out.println(" press 1 to edit the booking and any button to go back : ");
            switch (Integer.parseInt(input.next())){
                case 1 :
                    modifyBooking(booking);
                    break;
                default:
                    UserMenu.mainMenu();
                    break;
            }
        }
    public static  void  addTicket(Booking booking) throws IOException {
       Passenger passenger = Passenger.addPassenger();
       Seat seat = Seat.selectSeat(booking.flight.getSeats());
       Ticket ticket =Ticket.addTicket(seat,passenger);
       booking.passengers.add(passenger);
       booking.tickets.add(ticket);
       booking.seats.add(seat);
        int totalPaymentAmount = 0;
        for (Ticket t : booking.tickets){
            totalPaymentAmount+= Payment.calcPayAmount(t);
        }
        booking.payment =new Payment(totalPaymentAmount,booking.payment.getPayment_method());

    }
    }



