package SellTicket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;



    public class Airline {

        // Airline Seat Placement

        public static int[] PassengerList;
        public static HashMap<String, int[]> PassangerList2 = new HashMap<String, int[]>();
        public static HashMap<String, int[]> AirlineSeatList = new HashMap<>();

        public static String companyName;

        public static int  capacity;
        private static final int ticketPrice = 100;

        private static ArrayList<String> List = new ArrayList<>();
        private static Scanner scanner = new Scanner(System.in);
        private static int totalSeatNumber;
        private static Random random = new Random();
        private static LocalDateTime date = LocalDateTime.now();
        private static String passengerDetail = "";

        public static int[] GetAirlinesSeatList(String companyName) {                // if there is no list, create a new one or return the present one
            if (AirlineSeatList.get(companyName) == null) {
                System.out.println("There is no List");
                int[] newPassengerList = new int[totalSeatNumber];
                AirlineSeatList.put(companyName, newPassengerList);
            }
            return AirlineSeatList.get(companyName);
        }

        public static int[] GetAirlinesSeatList(String companyName, int[] passengerList) { //if the list already exists, return the list
            AirlineSeatList.put(companyName, passengerList);
            return AirlineSeatList.get(companyName);
        }

        public static void sellTicket() {

            System.out.println("Welcome! \nPlease choose the bus company: ");
            System.out.println("1. Turkish Airlines \n2. Eurowings \n3. France Air \n4. Luftansa");

            //THIS CODE WILL ADD NEW SEAT 0-1 PLANS FOR EVERY BUS AND ASK USER WHICH HE WANTS TO CHOOSE
            while (true) {
                switch (scanner.nextInt()) {
                    case 1:
                        companyName = "Turkish Airlines";
                        totalSeatNumber = 15;
                        break;
                    case 2:
                        companyName = "Eurowings";
                        totalSeatNumber = 15;
                        break;
                    case 3:
                        companyName = "France Air";
                        totalSeatNumber = 15;
                        break;
                    case 4:
                        companyName = "Lufthansa";
                        totalSeatNumber = 15;
                        break;
                    default:
                        System.out.println("Please enter the right number: ");
                }
                break;
            }
            PassengerList = GetAirlinesSeatList(companyName, GetAirlinesSeatList(companyName));          // bring the bus seat list(empty or occupied)
            capacity = PassengerList.length;

            PassangerList2.put(companyName, PassengerList);

            String seatNumber;
            // choosing seat number and entering costomer name for bus ....
            EmptySeatList();
            while (true) {
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

            askCostomer(seatNumber);

        }

        public static void askCostomer(String seatNumber) {
            System.out.println("Are you sure to buy this ticket?");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Yes")) {

                String TickedSold = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss"));            // Date is created..
                PassengerLog.SaveSoldTicketAirlines(TickedSold, passengerDetail);                                                     //Data saved.
                System.out.println("Your ticket is successfully reserved!");

            } else if (answer.equalsIgnoreCase("No")) {
                System.out.println("You've cancelled to buy this ticket!");

                PassangerList2.get(companyName)[Integer.parseInt(seatNumber) - 1] = 0;                                                       //Data is erased!!

                sellTicket();
            } else {
                System.out.println("Please enter a valid answer: ");
                askCostomer(seatNumber);
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

