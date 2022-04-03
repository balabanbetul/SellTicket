package SellTicket;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void newPassanger() {
        while (true) {
            System.out.println("Please choose your travel type: ");
            System.out.println("1. Bus\n2. Train\n3. Airlines");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1:
                    Bus.sellTicket();
                    break;
                case 2:
                    Train.sellTicket();
                    break;
                case 3:
                    Airline.sellTicket();
                    break;
                default:
                    System.out.println("Please enter a valid number: ");
            }
            break;
        }
    }

    public static void main(String[] args)  throws IOException {

        while (true){
            newPassanger();
            System.out.println("\n");
        }
    }
}
