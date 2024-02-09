package input_output;

import MainClasses.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    // Initializing constants to be used as file names
    public static final String USERS_FILE = "Users.txt";
    public static final String FLIGHTS_FILE = "Flights.txt";
    public static final String BOOKINGS_FILE = "Bookings.txt";
    public static final String AIRPORTS_FILE ="Airports.txt";
    public static final String TICKETS_FILE ="Tickets.txt";
    
    // Initializing global arrays
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Flight> flights = new ArrayList<Flight>();
    public static ArrayList<Booking> bookings = new ArrayList<Booking>();
    public static ArrayList<Airport> airports = new ArrayList<Airport>();
    public static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    public static void loadALL() throws IOException { // Calls the load method for each file
        load(USERS_FILE, users);
        load(FLIGHTS_FILE, flights);
        load(AIRPORTS_FILE, airports);
        load(BOOKINGS_FILE, bookings);
        load(TICKETS_FILE,tickets);
    }
    private static void load(String fileName, ArrayList objects) throws IOException{
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            Object obj ;
            while (true){
                try {
                    obj = ois.readObject();
                    if (obj!=null){
                        objects.add(obj);
                    }
                }
                catch (ClassNotFoundException | StreamCorruptedException e ) {
                    Output.saveALL();
                    ois.close();
                    return;
                }
        }
        }catch (EOFException e){
            return;
        }
        catch (InvalidClassException e){

        }
        catch (FileNotFoundException e){
            File file = new File(fileName);
            file.createNewFile();
            Input.loadALL();
        }
    }
}
