package MainClasses;
import Menus.AdminMenu;
import input_output.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
    private String username;
    private String password;
    public ArrayList<Booking> userBookings =new ArrayList<Booking>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        Input.users.add(this);
    }

    public ArrayList<Booking> getUserBookings() {
        return userBookings;
    }

    public void setUserBookings(ArrayList<Booking> userBookings) {
        this.userBookings = userBookings;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public static void listUsers() throws IOException {
        boolean exist = false;
        int i = 1;
        System.out.println("~~ Users List ~~");
        for(User user: Input.users){
            System.out.printf("%d)%s\n",i++,user.username);
            exist = true;
        }
        if (!exist){
            System.out.println("no users");
            AdminMenu.mainMenu();
        }else {
            System.out.println("press any button to exist");
            Scanner  input = new Scanner(System.in);
            switch (input.next()){
                default :
                    AdminMenu.mainMenu();
            }
        }

    }
}

