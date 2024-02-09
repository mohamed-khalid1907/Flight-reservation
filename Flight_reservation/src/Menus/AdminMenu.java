package Menus;

import input_output.*;
import MainClasses.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu extends Menu{
    public static void mainMenu() throws IOException {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.println("~~ Welcome Admin ~~\n1)Flights menu\n2)Users menu\n3)Airport menu\n4)Log out\n5)Quit and Save\n");
            System.out.println("-> Enter Your choice  ");
            int choice = Integer.parseInt(scanner.next());
            switch(choice){
                case 1: flightsMenu(); break;
                case 2: usersMenu(); break;
                case 3: airportMenu();break;
                case 4: StartMenu.mainMenu();
                    Output.saveALL();
                    break;
                case 5:
                    Output.saveALL();
                    return;
                default: System.out.println("Please choose a number from the list and try again"); mainMenu(); break;
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            mainMenu();
        }

    }
    private static void flightsMenu() throws IOException {
        try {
            System.out.println("~~ Flights menu ~~\n1)Add flight\n2)Modify flight\n3)Remove flight\n4)Go back\n");
            System.out.println("-> Enter Your choice  ");
            Scanner scanner=new Scanner(System.in);
            int choice = Integer.parseInt(scanner.next());
            switch (choice){
                case 1: Flight.AddFlight();
                    mainMenu();break;
                case 2: Flight.EditFlight();
                    mainMenu();break;
                case 3: Flight.removeFlight();
                    mainMenu();break;
                case 4: mainMenu();
                break;
                default: System.out.println("Please choose a number from the list and try again"); flightsMenu(); break;
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            flightsMenu();
        }

    }
    private static void usersMenu() throws IOException {
            User.listUsers();
    }
    private static void airportMenu() throws IOException {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.println("~~ Airport menu ~~\n1)Add Airport\n2)Remove Airport\n3)Go back\n");
            System.out.println("-> Enter Your choice  ");
            int choice = Integer.parseInt(scanner.next());
            switch (choice){
                case 1: Airport.addAirport();
                    mainMenu();
                    break;
                case 2: Airport.removeAirport();
                    mainMenu(); break;
                case 3: mainMenu();break;
                default: System.out.println("Please choose a number from the list and try again"); mainMenu(); break;
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            airportMenu();
        }
    }

}
