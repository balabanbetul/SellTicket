package SellTicket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Train {

    // Train Seat Placement

    public static int[] PassengerList;
    public static HashMap<String, int[]> PassangerList2 = new HashMap<>();
    public static HashMap<String, int[]> TrainSeatList = new HashMap<>();

    private static int  capacity;
    private static final int ticketPrice = 20;

    private static ArrayList<String> List = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int totalSeatNumber;
    public static String companyName;
    private static Random random = new Random();
    private static LocalDateTime date = LocalDateTime.now();
    private static String passengerDetail = "";


    public static int[] GetTrainSeatList(String companyName) {                // if there is no list, create a new one or return the present one
        if (TrainSeatList.get(companyName) == null) {
            System.out.println("There is no List");
            int[] newPassengerList = new int[totalSeatNumber];
            TrainSeatList.put(companyName, newPassengerList);
        }
        return TrainSeatList.get(companyName);
    }

    public static int[] GetTrainSeatList(String companyName, int[] passengerList) { //if the list already exists, return the list
        TrainSeatList.put(companyName, passengerList);
        return TrainSeatList.get(companyName);
    }

    public static void sellTicket() {

        System.out.println("Welcome! \nPlease choose the bus company: ");
        System.out.println("1. U-Bahn \n2. S-Bahn \n3. IRE \n4. ICE");

        //THIS CODE WILL ADD NEW SEAT 0-1 PLANS FOR EVERY BUS AND ASK USER WHICH HE WANTS TO CHOOSE
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    companyName = "U-Bahn";
                    totalSeatNumber = 10;
                    break;
                case 2:
                    companyName = "S-Bahn";
                    totalSeatNumber = 10;
                    break;
                case 3:
                    companyName = "IRE";
                    totalSeatNumber = 10;
                    break;
                case 4:
                    companyName = "ICE";
                    totalSeatNumber = 10;
                    break;
                default:
                    System.out.println("Please enter the right number: ");
            }
            break;
        }

        PassengerList = GetTrainSeatList(companyName, GetTrainSeatList(companyName));          // bring the bus seat list(empty or occupied)
        capacity = PassengerList.length;

        PassangerList2.put(companyName, PassengerList);


        String seatNumber;
        // choosing seat number and entering costomer name for bus ....
        while (true) {
            EmptySeatList();
            System.out.println("Please choose your seat or type Random: ");
            scanner.nextLine();
            seatNumber = scanner.nextLine();
            if (seatNumber.isEmpty()) {
            } else if (seatNumber.equalsIgnoreCase("random")) {
                seatNumber = String.valueOf(random.nextInt(capacity) + 1); // seatNumber gets a random value.
                if (IsSeatEmpty(Integer.parseInt(seatNumber) - 1, PassangerList2.get(companyName))) {
                    PassangerList2.get(companyName)[Integer.parseInt(seatNumber) - 1] = 1;
                    System.out.println("Seat number: " + seatNumber);
                    System.out.print("Please enter your name: ");
                    passengerDetail = "" + seatNumber + " seat: " + scanner.nextLine();
                    break;
                }
            } else {
                try {
                    if (Integer.parseInt(seatNumber) > 0 && Integer.parseInt(seatNumber) < capacity + 1) {
                        if (IsSeatEmpty(Integer.parseInt(seatNumber) - 1, PassangerList2.get(companyName))) {
                            PassangerList2.get(companyName)[Integer.parseInt(seatNumber) - 1] = 1;
                            System.out.println("Seat number: " + seatNumber);
                            System.out.print("Please enter your name: ");
                            passengerDetail = "" + seatNumber + " seat: " + scanner.nextLine();
                            break;
                        }
                    }
                }catch (NumberFormatException e){
                    System.out.println("You entered wrong input!\nPlease choose your seat: ");
                }
            }
        }

        askCustomer(seatNumber);

    }

    public static void askCustomer(String seatNumber) {
        System.out.println("Are you sure to buy this ticket?");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("Yes")) {

            String TickedSold = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss"));            // Date is created..
            PassengerLog.SaveSoldTicketTrain(TickedSold, passengerDetail);                                                     //Data saved.
            System.out.println("Your ticket is successfully reserved!");

        } else if (answer.equalsIgnoreCase("No")) {
            System.out.println("You've cancelled to buy this ticket!");

            PassangerList2.get(companyName)[Integer.parseInt(seatNumber) - 1] = 0;                                                       //Data is erased!!

            sellTicket();
        } else {
            System.out.println("Please enter a valid answer: ");
            askCustomer(seatNumber);
        }
    }

    public static void TotalsoldSeat(int ticketPrice, HashMap<String, String> ticketSoldDate) {
        System.out.println("Sum: " + ticketPrice * ticketSoldDate.size());
    }

    public static void EmptySeatList() {
        for (int i = 0; i < PassangerList2.get(companyName).length; i++) {
            if (IsSeatEmpty(i, PassangerList2.get(companyName))) {
                System.out.print(companyName + "\t");
                System.out.println(i + 1 + ". is empty");
            }
        }
    }

    public static boolean IsSeatEmpty(int seatNumber, int[] PassangerList) {
        if (PassangerList[seatNumber] == 0) {
            return true;
        } else return false;
    }
}
