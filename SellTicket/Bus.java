package SellTicket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static SellTicket.PassengerLog.SaveSoldTicketBus;

public class Bus {
    // Bus Seat Placement

    public static int[] PassengerList;
    public static HashMap<String, int[]> PassangerList2 = new HashMap<>();
    public static HashMap<String, int[]> busSeatList = new HashMap<>();

    public static int capacity;
    private static final int ticketPrice = 30;


    private static ArrayList<String> List = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int totalSeatNumber;
    public static String companyName;
    private static Random random = new Random();
    private static LocalDateTime date = LocalDateTime.now();
    private static String passengerDetail = "";


    public static int[] GetBusSeatList(String companyName) {                // if there is no list, create a new one or return the present one
        if (busSeatList.get(companyName) == null) {
            System.out.println("There is no List");
            int[] newPassengerList = new int[totalSeatNumber];
            busSeatList.put(companyName, newPassengerList);
        }
        return busSeatList.get(companyName);
    }

    public static int[] GetBusSeatList(String companyName, int[] passengerList) { //if the list already exists, return the list
        busSeatList.put(companyName, passengerList);
        return busSeatList.get(companyName);
    }

    public static void sellTicket() {

        System.out.println("Welcome! \nPlease choose the bus company: ");
        System.out.println("1. Flixbus \n2. Egetur \n3. Aydoganlar \n4. Has Adana");

        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    companyName = "Flixbus";
                    totalSeatNumber = 4;
                    break;
                case "2":
                    companyName = "Egetur";
                    totalSeatNumber = 4;
                    break;
                case "3":
                    companyName = "Aydoganlar";
                    totalSeatNumber = 4;
                    break;
                case "4":
                    companyName = "Has Adana";
                    totalSeatNumber = 4;
                    break;
                default:
                    System.out.println("Please enter the right number: ");
            }
            break;
        }

        PassengerList = GetBusSeatList(companyName, GetBusSeatList(companyName));          // bring the bus seat list(empty or occupied)
        capacity = PassengerList.length;

        PassangerList2.put(companyName, PassengerList);

        String seatNumber;
        // choosing seat number and entering customer name for bus ....
        while (true) {
            EmptySeatList();
            System.out.println("Please choose your seat or type Random: ");
            //scanner.nextLine();
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
            SaveSoldTicketBus(TickedSold, passengerDetail);                                                     //Data saved.
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

    public static void TotalSoldSeat(int ticketPrice, HashMap<String, String> ticketSoldDate) {
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

    public static boolean IsSeatEmpty(int seatNumber, int[] PassangerList)
    {
        return PassangerList[seatNumber] == 0;
    }
}
