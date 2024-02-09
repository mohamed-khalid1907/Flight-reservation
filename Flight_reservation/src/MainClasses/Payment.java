package MainClasses;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public  class Payment implements Serializable {
 private final  int payment_id;
    private int payment_amount;
    private String payment_method;
     private Boolean status;

    public Payment(int payment_amount,  String payment_method) {
        this.payment_amount=payment_amount;
        this.payment_method = payment_method;
       this.status=true;
       Random rand=new Random();
       this.payment_id=rand.nextInt(99999 - 10000 + 1) + 10000;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public int getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(int payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
    public static String chooseMethod(){
        String way = null;
        try {
            Scanner in =new Scanner(System.in);
            System.out.println("please choose method ");
            System.out.println("1:credit card");
            System.out.println("2:debit card");
            System.out.println("3:digital wallet");
            int choice = Integer.parseInt(in.next());
            switch (choice){
                case 1:way= "credit card";
                    break;
                case 2: way= "default card";
                    break;
                case 3 : way= "digital wallet";
                    break;
                default:System.out.println("Please Enter a number on the menu and try again");chooseMethod();break;

            }
        }catch (InputMismatchException | NumberFormatException  e){
            System.out.println("Please Enter a number on the menu and try again");
            chooseMethod();
        }
        return way;
    }
    public static int calcPayAmount(Ticket t){
        final int FEES=1000;
        int ticket_fare=t.getFare();
        int taxese=(14/100)*ticket_fare;
        return ticket_fare+taxese+FEES;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}

