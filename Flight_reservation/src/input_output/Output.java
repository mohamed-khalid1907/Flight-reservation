package input_output;
import MainClasses.*;

import java.io.*;
import java.util.ArrayList;
public class Output {
    public static void saveALL() throws IOException { // Calls the save file method for each array
        saveFile(Input.users,Input.USERS_FILE);
        saveFile(Input.bookings, Input.BOOKINGS_FILE);
        saveFile(Input.flights, Input.FLIGHTS_FILE);
        saveFile(Input.airports,Input.AIRPORTS_FILE);
        saveFile(Input.tickets,Input.TICKETS_FILE);
    }
    private static void saveFile(ArrayList objects, String fileName) {
        try {
            ObjectOutputStream objectWriter = new ObjectOutputStream(new FileOutputStream(fileName));
            if (objects.isEmpty()){ // Checks if the first index of the array is null which means the array has no file, so it makes a new empty file for the array
                return;
            }
            for (Object obj : objects) { // Loops over each element in objects and writes it into the file
                if (obj != null) {
                    objectWriter.writeObject(obj);
                    objectWriter.flush();
                }
            }
            objectWriter.close();
        } catch (IOException e) {
            System.out.println("There was a problem Writing files");
            throw new RuntimeException(e);
        }
    }
}