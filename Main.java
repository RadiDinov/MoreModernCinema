package ClassWork_08_02;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);

    private static String UInput;
    private static double UInputDouble;
    private static int UInputInt;

    private static double finalSum;

    //OBJECTS
    static DiscountTicketInterface discountTicket = new DiscountTicket();
    static GroupTicketInterface groupTicket = new GroupTicket();
    //OBJECTS

    static JDBC insertNewFilm, updateDiscountTicket, updateGroupTicketRequirement, updateGroupTicketDiscount;

    static {
        try {
            insertNewFilm = new JDBC("select * from ticketsclass", "INSERT INTO ticketsclass (filmName, filmPrice) VALUES (?, ?)");
            updateDiscountTicket = new JDBC("select * from ticketsoptions", "UPDATE ticketsoptions SET discountTicket = ?");
            updateGroupTicketRequirement = new JDBC("select * from ticketsoptions", "UPDATE ticketsoptions SET groupTicketRequirement = ?");
            updateGroupTicketDiscount = new JDBC("select * from ticketsoptions", "UPDATE ticketsoptions SET groupTicketDiscount = ?");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static ArrayList<Ticket> tickets = new ArrayList<>();



    public static void main(String[] args) throws SQLException {


        while (insertNewFilm.resultSet.next()) {
            String filmName = insertNewFilm.resultSet.getString("filmName");
            String filmPrice = insertNewFilm.resultSet.getString("filmPrice");
            tickets.add(new Ticket(filmName, Double.parseDouble(filmPrice)));
        }
        while(updateDiscountTicket.resultSet.next()) {
            discountTicket.setTicketDiscount(updateDiscountTicket.resultSet.getDouble("discountTicket"));
            groupTicket.setGroupTicketCountRequirement(updateDiscountTicket.resultSet.getInt("groupTicketRequirement"));
            groupTicket.setGroupTicketPricePerPersonDiscount(updateDiscountTicket.resultSet.getDouble("groupTicketDiscount"));
        }





        //UI
        System.out.println("Welcome to Rado FILMS");
        System.out.println("Please, choose an option (1-9):");
        System.out.println("(1)View films"); //done
        System.out.println("(2)Buy tickets");
        System.out.println("(3)Add films"); //done
        System.out.println("(4)Edit films"); //done
        System.out.println("(5)Edit tickets"); //done
        System.out.println("(6)View default ticket options"); //done
        System.out.println("(7)View current ticket options"); //done
        System.out.println("(8)View bill"); //done
        System.out.println("(9)End of work day");
        String input = scan.nextLine().toLowerCase();
        while (!input.equals("9")) {
            switch (input) {
                case "1": {
                    viewFilms();
                    break;
                }
                case "2": {
                    buyTickets();
                    break;
                }
                case "3": {
                    addFilms();
                    break;
                }
                case "4": {
                    editFilms();
                    break;
                }
                case "5": {
                    editTickets();
                    break;
                }
                case "6": {
                    viewDefaultTicketOptions();
                    break;
                }
                case "7": {
                    viewCurrentTicketOptions();
                    break;
                }
                case "8": {
                    viewBill();
                    break;
                }
            }


            System.out.println("Please, choose an option (1-9):");
            System.out.println("(1)View films"); //done
            System.out.println("(2)Buy tickets");
            System.out.println("(3)Add films"); //done
            System.out.println("(4)Edit films"); //done
            System.out.println("(5)Edit tickets"); //done
            System.out.println("(6)View default ticket options"); //done
            System.out.println("(7)View current ticket options");
            System.out.println("(8)View bill"); //done
            System.out.println("(9)End of work day");
            input = scan.nextLine().toLowerCase();
        }
    }
    //UI


    public static void viewFilms() {
        if (!(tickets.size() == 0)) {
            System.out.println("Would you like to view films' tickets (1)with discounts or only with (2)normal price: ");
            UInputInt = Integer.parseInt(scan.nextLine());
            switch (UInputInt) {
                case 1: {
                    System.out.println("\n\n!!! EVERY TICKET IS FOR ONE PERSON !!!\n");
                    System.out.println("Currently added films with discounts: " + tickets.size());
                    for (Ticket ticket : tickets) {
                        System.out.println("==============================");
                        System.out.println("Film name: " + ticket.getTicketName() + "\n");
                        System.out.println("|---NORMAL TICKET---|");
                        System.out.printf("Price: " + "%.2f€\n", ticket.getTicketPrice());
                        System.out.println("|---DISCOUNT TICKET---|");
                        System.out.printf("Price: " + "%.2f€ (SAVING: %.2f€)\n", (ticket.getTicketPrice() - (ticket.getTicketPrice() * discountTicket.getTicketDiscount() / 100)), ticket.getTicketPrice() - (ticket.getTicketPrice() - (ticket.getTicketPrice() * discountTicket.getTicketDiscount() / 100)));
                        System.out.print("Discount: " + discountTicket.getTicketDiscount() + "%\n");
                        System.out.print("Requirements for discount: special document" + "\n");
                        System.out.println("|---GROUP TICKET---|");
                        System.out.printf("Price: " + "%.2f€ (SAVING: %.2f€)\n", (ticket.getTicketPrice() - (ticket.getTicketPrice() * (groupTicket.getGroupTicketPricePerPersonDiscount() / 100))), (ticket.getTicketPrice() - (ticket.getTicketPrice() - (ticket.getTicketPrice() * (groupTicket.getGroupTicketPricePerPersonDiscount() / 100)))));
                        System.out.print("Discount: " + groupTicket.getGroupTicketPricePerPersonDiscount() + "%\n");
                        System.out.print("Requirements for discount: " + groupTicket.getGroupTicketCountRequirement() + " people\n");
                        System.out.println("==============================\n\n");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Currently added films with normal prices: " + tickets.size());
                    for (Ticket ticket : tickets) {
                        System.out.println("==============================");
                        System.out.println("Film name: " + ticket.getTicketName());
                        System.out.println("|---NORMAL TICKET---|");
                        System.out.printf("Price: " + "%.2f€\n", ticket.getTicketPrice());
                        System.out.println("==============================\n\n");
                    }
                    break;
                }
            }
        } else {
            System.out.println("There aren't any films added!\n");
        }
    }


    public static void editTickets() throws SQLException { //set ticket discount, set groupTicketCountRequirement set groupTicketPricePerPersonDiscount
        System.out.println("Which ticket would you like to edit: ");
        System.out.println("(1)Discount ticket");
        System.out.println("(2)Group ticket");
        UInputInt = Integer.parseInt(scan.nextLine());
        switch (UInputInt) {
            case 1: {
                System.out.print("Please enter new discount: ");
                UInputDouble = Double.parseDouble(scan.nextLine());
                System.out.println("Successfully changed discount from " + discountTicket.getTicketDiscount() + "% to " + UInputDouble + "%\n");
                updateDiscountTicket.writeData.setString(1, String.valueOf(UInputDouble));
                updateDiscountTicket.writeData.executeUpdate();
                discountTicket.setTicketDiscount(UInputDouble);
                break;
            }
            case 2: {
                System.out.println("Change group ticket's (1)people requirement or (2)discount: ");
                UInput = scan.nextLine();
                if (UInput.equals("1")) {
                    System.out.print("Please enter new people requirement: ");
                    UInputInt = Integer.parseInt(scan.nextLine());
                    System.out.println("Successfully changed people requirement from " + groupTicket.getGroupTicketCountRequirement() + " to " + UInputInt + "\n");
                    updateGroupTicketRequirement.writeData.setString(1, String.valueOf(UInputInt));
                    updateGroupTicketRequirement.writeData.executeUpdate();
                    groupTicket.setGroupTicketCountRequirement(UInputInt);
                } else if (UInput.equals("2")) {
                    System.out.print("Please enter discount: ");
                    UInputDouble = Double.parseDouble(scan.nextLine());
                    updateGroupTicketDiscount.writeData.setString(1, String.valueOf(UInputDouble));
                    updateGroupTicketDiscount.writeData.executeUpdate();
                    System.out.println("Successfully changed discount from " + groupTicket.getGroupTicketPricePerPersonDiscount() + "% to " + UInputDouble + "%\n");
                    groupTicket.setGroupTicketPricePerPersonDiscount(UInputDouble);
                }
                break;
            }
        }
    }


    public static void editFilms() {
        if (!(tickets.size() == 0)) {
            boolean match = false;
            System.out.println("Which film would you like to edit: ");
            for (Ticket ticket : tickets) {
                System.out.println("-> Name: " + ticket.getTicketName() + "|| Price: " + ticket.getTicketPrice() + "€");
            }
            System.out.print("Answer: ");
            UInput = scan.nextLine();
            for (Ticket ticket : tickets) {
                if (ticket.getTicketName().equals(UInput)) {
                    System.out.println("Change ticket's (1)name, (2)price or (3)both: ");
                    UInput = scan.nextLine().toLowerCase();
                    if (UInput.equals("1")) {
                        System.out.print("Please enter new name: ");
                        UInput = scan.nextLine();
                        System.out.println("Successfully changed name from " + ticket.getTicketName() + " to " + UInput);
                        ticket.setTicketName(UInput);
                    } else if (UInput.equals("2")) {
                        System.out.print("Please enter new price: ");
                        UInputDouble = Double.parseDouble(scan.nextLine());
                        System.out.println("Successfully changed price from " + ticket.getTicketPrice() + "€ to " + UInputDouble + "€");
                        ticket.setTicketPrice(UInputDouble);
                    } else if (UInput.equals("3")) {
                        System.out.print("Please enter new name: ");
                        UInput = scan.nextLine();
                        System.out.println("Successfully changed name from " + ticket.getTicketName() + " to " + UInput);
                        ticket.setTicketName(UInput);
                        System.out.print("Please enter new price: ");
                        UInputDouble = Double.parseDouble(scan.nextLine());
                        System.out.println("Successfully changed price from " + ticket.getTicketPrice() + "€ to " + UInputDouble + "€");
                        ticket.setTicketPrice(UInputDouble);
                    }
                    match = true;
                    break;
                }
            }
            if (!match) {
                System.out.println("There is no film name: " + UInput);
            }
        } else {
            System.out.println("There aren't any films added!\n");
        }
    }


    public static void viewBill() {
        System.out.printf("Current bill: %.2f€\n", finalSum);
    }


    public static void addFilms() {
        String input = "1";
        while (!input.equals("2")) {
            System.out.println("\n\nStarting to add films");
            System.out.print("Insert film name: ");
            String filmName = scan.nextLine();
            System.out.print("Insert film price: ");
            double filmPrice = Double.parseDouble(scan.nextLine());
            Ticket newTicket = new Ticket(filmName, filmPrice);
            tickets.add(newTicket);
            System.out.println("Successfully added a film!\n(1)Add another one\n(2)Stop adding");
            try {
                insertNewFilm.writeData.setString(1, filmName);
                insertNewFilm.writeData.setString(2, String.valueOf(filmPrice));
                insertNewFilm.writeData.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            input = scan.nextLine();
        }
    }

    public static void viewDefaultTicketOptions() {
        System.out.println("Default options:");
        System.out.println("|---DISCOUNT TICKET---|");
        System.out.println("Discount: 15%");
        System.out.println("|---GROUP TICKET---|");
        System.out.println("Discount: 25%");
        System.out.println("Requirements for discount: 5 people\n");
        System.out.println("Would you like to set these options: (1)Yes or (2)No");
        UInputInt = Integer.parseInt(scan.nextLine());
        switch (UInputInt) {
            case 1: {
                discountTicket.setTicketDiscount(15.00);
                groupTicket.setGroupTicketCountRequirement(5);
                groupTicket.setGroupTicketPricePerPersonDiscount(25.00);
                System.out.println("Successfully updated with default ticket options\n");
                break;
            }
            case 2: {
                break;
            }
        }
    }


    public static void viewCurrentTicketOptions() {
        System.out.println("|---DISCOUNT TICKET---|");
        System.out.println("Discount: " + discountTicket.getTicketDiscount() + "%");
        System.out.println("|---GROUP TICKET---|");
        System.out.println("Discount: " + groupTicket.getGroupTicketPricePerPersonDiscount() + "%");
        System.out.println("Requirements for discount: " + groupTicket.getGroupTicketCountRequirement() + " people\n");
    }


    public static void buyTickets() {
        boolean successfulPurchase = false;
        boolean noFilmName = false;
        String input = "1";
        while (!input.equals("2")) {
            System.out.println("\n\nStarting to buy tickets");
            System.out.print("For how many people: ");
            int people = Integer.parseInt(scan.nextLine());
            System.out.print("Film name: ");
            String filmName = scan.nextLine();
            for (Ticket ticket : tickets) {
                if (ticket.getTicketName().equals(filmName)) {
                    if (people >= groupTicket.getGroupTicketCountRequirement()) {
                        System.out.println("Using group ticket price.");
                        finalSum += people * (ticket.getTicketPrice() - (ticket.getTicketPrice() * (groupTicket.getGroupTicketPricePerPersonDiscount() / 100)));
                        successfulPurchase = true;
                        noFilmName = false;
                        break;
                    } else {
                        System.out.println("Using normal ticket price, because you don't have the necessary requirements.");
                        if (people == 1) {
                            System.out.println("Do you have the necessary document: ");
                        } else {
                            System.out.println("Does everyone have the necessary document: ");
                        }
                        System.out.println("(1)Yes");
                        System.out.println("(2)No");
                        System.out.print("Answer: ");
                        UInput = scan.nextLine();
                        switch (UInput) {
                            case "1": {
                                System.out.println("Using discount ticket price.");
                                finalSum += people * (ticket.getTicketPrice() - (ticket.getTicketPrice() * discountTicket.getTicketDiscount() / 100));
                                successfulPurchase = true;
                                noFilmName = false;
                                break;
                            }
                            case "2": {
                                System.out.println("Using normal ticket price, because you don't have the necessary document too.");
                                finalSum += people * ticket.getTicketPrice();
                                successfulPurchase = true;
                                noFilmName = false;
                                break;
                            }
                            default: {
                                System.out.println("There is not an option: " + UInput);
                                break;
                            }
                        }
                    }
                } else {
                    noFilmName = true;
                }
            }

            if (successfulPurchase) {
                System.out.printf("Current bill: %.2f€\n", finalSum);
                if (people == 1) {
                    System.out.print("Successfully bought a ticket!\n(1)Buy another one\n(2)Stop buying\nAnswer: ");
                } else if (people > 1) {
                    System.out.print("Successfully bought tickets!\n(1)Buy another one\n(2)Stop buying\nAnswer: ");
                }
                successfulPurchase = false;
                input = scan.nextLine();
            } else if (noFilmName) {
                System.out.println("Sorry, but there is no film with this name: " + filmName);
                System.out.print("\n(1)Buy a ticket\n(2)Stop buying\nAnswer: ");
                input = scan.nextLine();
            }
        }
    }

}