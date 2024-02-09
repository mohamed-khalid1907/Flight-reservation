package Menus;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import input_output.*;
import MainClasses.*;
public class UserMenu extends Menu {
    public static void mainMenu() throws IOException {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.printf("~~ Welcome %s ~~\n1)Bookings menu\n2)Change Password\n3)Log out\n4)Quit and Save\n", activeUser.getUsername());
            System.out.println("-> Enter Your choice  ");
            int choice = Integer.parseInt(scanner.next());
            switch(choice){
                case 1: bookingsMenu(); break;
                case 2: changePassword(); break;
                case 3: StartMenu.mainMenu();
                    break;
                case 4: Output.saveALL();
                    return;
                default: System.out.println("Please choose a number from the list and try again"); mainMenu(); break;
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            mainMenu();
        }

    }
    private static void bookingsMenu() throws IOException {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.println("~~ Booking menu ~~\n1)Booking List\n2)Add Booking\n3)Modify Booking\n4)Remove Booking\n5)Go back\n");
            System.out.println("-> Enter Your choice  ");
            int choice = Integer.parseInt(scanner.next());
            switch(choice){
                case 1: Booking.displayUserBookings(); break;
                case 2: Booking.addBooking(); break;
                case 3: Booking.modifyBooking(); break;
                case 4: Booking.removeBooking(); break;
                case 5: mainMenu(); break;
                default: System.out.println("Please choose a number from the list and try again"); mainMenu(); break;
            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            mainMenu();
        }

    }
    private static void changePassword() throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your new password (-1 to go back)");
        String newPassword = scanner.next();
        if (newPassword.equals("-1")){
            mainMenu();
        } else {
            activeUser.setPassword(newPassword);
        }
    }
}
