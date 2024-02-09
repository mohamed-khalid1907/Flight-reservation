package Menus;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import input_output.*;
import MainClasses.*;


public class StartMenu {
    public static void mainMenu() throws IOException  {
try {
    System.out.println("~~ Welcome to UNNAMED Airlines Reservation App! ~~\n1)Login\n2)Register\n3)Forget Password\n4)Quit and Save");
    System.out.println("-> Enter Your choice  ");
    Scanner input = new Scanner(System.in);
    int choice = Integer.parseInt(input.next());
    switch (choice){
        case 1: login(); break;
        case 2: register(); break;
        case 3: forgetPassword(); break;
        case 4: Output.saveALL(); System.exit(0); break;
        default: System.out.println("Please Enter a number on the menu and try again"); mainMenu(); break;
    }
}catch (InputMismatchException | NumberFormatException  e){
    System.out.println("Please Enter a number on the menu and try again");
    mainMenu();
}


    }

    private static void register() throws IOException {
        System.out.println("~~ Register ~~\nPlease enter a username without spaces: (-1 to go back)");
        Scanner input = new Scanner(System.in);
        String createdUsername = input.next();
        if(createdUsername.equals("-1")){
            mainMenu();
        }
        System.out.println("Please enter a password without spaces:");
        String createdPassword = input.next();
        try {
            for(User user: Input.users){
                if(user.getUsername().equals(createdUsername)){
                    System.out.printf("ERROR: username \"%s\" is already taken please use another username",createdUsername);
                    register();
                }
            }
            new User(createdUsername,createdPassword);
        } catch (Exception e) {
            System.out.println("There was a problem creating your user");
        }
        mainMenu();
        Output.saveALL();
    }

    private static void login() throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("~~ Login ~~\nPlease enter your Username: ( Enter -1 to go back ) ");
        String enteredUsername = scanner.next();
        if(enteredUsername.equals("-1")){
            mainMenu();
        }
        System.out.println("Please enter your password : ( Enter -1 to go back )");
        String enteredPassword = scanner.next();
        if(enteredPassword.equals("-1")){
            mainMenu();
        }
        if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
            System.out.println("Success! Welcome Admin");
            AdminMenu.mainMenu();
            return;
        } else {
            for(User user: Input.users){
                if (enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword())) {
                    System.out.printf("Success! Welcome %s\n", user.getUsername());
                    Menu.activeUser = user;
                    UserMenu.mainMenu();
                    return;
                }
            }
        }
        System.out.println("Failure!\n1)Go back to menu\nTry again by pressing any other number");
        if (scanner.nextInt() == 1) {
            mainMenu();
        } else {
            login();
        }

    }

    private static void forgetPassword() throws IOException {
        String username;
        Scanner scanner=new Scanner(System.in);
        System.out.println("~~ Forget Password ~~\nPlease enter your username: (-1 to go back)");
        username = scanner.next();
        if(username.equals("-1")){
            mainMenu();
        } else {
            for(User user: Input.users){
                if(username.equals(user.getUsername())){
                    System.out.println("Enter your new password");
                    user.setPassword(scanner.next());
                    mainMenu();
                }
            }
            System.out.println("Username was not found.");
            mainMenu();
        }
    }

}
