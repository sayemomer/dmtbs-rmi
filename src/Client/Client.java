package Client;

import DataModel.MovieModel;
import Interface.MovieManagementInterface;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static final int CUSTOMER_BOOK_MOVIE = 1;



    public static final int MANAGER_ADD_MOVIE =1 ;



    public static final int SERVER_ATWATER = 2964;
    public static final int SERVER_VERDUN = 2965;
    public static final int SERVER_OUTRAMONT = 2966;
    public static final String MOVIE_MANAGEMENT_REGISTERED_NAME = "MOVIE_MANAGEMENT";

    public static final int USER_TYPE_CUSTOMER = 1;
    public static final int USER_TYPE_ADMIN = 2;

    static Scanner input;
    public static void main(String[] args) throws Exception {
        init();
    }

    public static void init() throws IOException {
        input = new Scanner(System.in);
        String userID;
        System.out.println("Please Enter your UserID:");
        userID = input.next().trim().toUpperCase();
//        Logger.clientLog(userID, " login attempt");
        switch (checkUserType(userID)) {
            case USER_TYPE_CUSTOMER:
                try {
                    System.out.println("Customer Login successful (" + userID + ")");
//                    Logger.clientLog(userID, " Customer Login successful");
                    customer(userID, getServerPort(userID.substring(0, 3)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case USER_TYPE_ADMIN:
                try {
                    System.out.println("Manager Login successful (" + userID + ")");
//                    Logger.clientLog(userID, " Manager Login successful");
                    admin(userID, getServerPort(userID.substring(0, 3)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("!!UserID is not in correct format");
//                Logger.clientLog(userID, " UserID is not in correct format");
//                Logger.deleteALogFile(userID);
                init();
        }
    }

    private static int getServerPort(String branchAcronym) {
        if (branchAcronym.equalsIgnoreCase("ATW")) {
            return SERVER_ATWATER;
        } else if (branchAcronym.equalsIgnoreCase("OUT")) {
            return SERVER_OUTRAMONT;
        } else if (branchAcronym.equalsIgnoreCase("VER")) {
            return SERVER_VERDUN;
        }
        return 1;
    }


    private static int checkUserType(String userID) {
        if (userID.length() == 8) {
            if (userID.substring(0, 3).equalsIgnoreCase("ATW") ||
                    userID.substring(0, 3).equalsIgnoreCase("OUT") ||
                    userID.substring(0, 3).equalsIgnoreCase("VER")) {
                if (userID.substring(3, 4).equalsIgnoreCase("C")) {
                    return USER_TYPE_CUSTOMER;
                } else if (userID.substring(3, 4).equalsIgnoreCase("A")) {
                    return USER_TYPE_ADMIN;
                }
            }
        }
        return 0;
    }


    private static void customer(String customerID, int serverPort) throws Exception {
        if (serverPort == 1) {
            return;
        }
        Registry registry = LocateRegistry.getRegistry(serverPort);
        MovieManagementInterface remoteObject = (MovieManagementInterface) registry.lookup(MOVIE_MANAGEMENT_REGISTERED_NAME);
        boolean repeat = true;
        printMenu(USER_TYPE_CUSTOMER);
        int menuSelection = input.nextInt();
        String movieType;
        String movieID;
        String serverResponse;
        switch (menuSelection) {
            case CUSTOMER_BOOK_MOVIE:
                movieType = promptForMovieType();
                movieID = promptForMovieID();
                //Logger.clientLog(customerID, " attempting to bookEvent");
                serverResponse = remoteObject.bookEvent(customerID, movieID, movieType);
                System.out.println(serverResponse);
                //Logger.clientLog(customerID, " bookEvent", " eventID: " + eventID + " eventType: " + eventType + " ", serverResponse);
                break;
//            case CUSTOMER_GET_BOOKING_SCHEDULE:
//                Logger.clientLog(customerID, " attempting to getBookingSchedule");
//                serverResponse = remoteObject.getBookingSchedule(customerID);
//                System.out.println(serverResponse);
//                Logger.clientLog(customerID, " bookEvent", " null ", serverResponse);
//                break;
//            case CUSTOMER_CANCEL_EVENT:
//                eventType = promptForEventType();
//                eventID = promptForEventID();
//                Logger.clientLog(customerID, " attempting to cancelEvent");
//                serverResponse = remoteObject.cancelEvent(customerID, eventID, eventType);
//                System.out.println(serverResponse);
//                Logger.clientLog(customerID, " bookEvent", " eventID: " + eventID + " eventType: " + eventType + " ", serverResponse);
//                break;
//            case CUSTOMER_LOGOUT:
//                repeat = false;
//                Logger.clientLog(customerID, " attempting to Logout");
//                init();
//                break;
        }
        if (repeat) {
            customer(customerID, serverPort);
        }
    }

    private static void admin(String eventManagerID, int serverPort) throws Exception {

        System.out.println(serverPort);
        if (serverPort == 1) {
            return;
        }
        Registry registry = LocateRegistry.getRegistry(serverPort);
        MovieManagementInterface remoteObject = (MovieManagementInterface) registry.lookup(MOVIE_MANAGEMENT_REGISTERED_NAME);
        boolean repeat = true;
        printMenu(USER_TYPE_ADMIN);
        String customerID;
        String movieType;
        String movieID;
        String serverResponse;
        int capacity;
        int menuSelection = input.nextInt();
        switch (menuSelection) {
            case MANAGER_ADD_MOVIE:
                movieType = promptForMovieType();
                movieID = promptForMovieID();
                capacity = promptForCapacity();
               // Logger.clientLog(eventManagerID, " attempting to addEvent");
                serverResponse = remoteObject.addEvent(movieID, movieType, capacity);
                System.out.println(serverResponse);
                //Logger.clientLog(eventManagerID, " addEvent", " eventID: " + eventID + " eventType: " + eventType + " eventCapacity: " + capacity + " ", serverResponse);
                break;
//            case MANAGER_REMOVE_EVENT:
//                eventType = promptForEventType();
//                eventID = promptForEventID();
//                Logger.clientLog(eventManagerID, " attempting to removeEvent");
//                serverResponse = remoteObject.removeEvent(eventID, eventType);
//                System.out.println(serverResponse);
//                Logger.clientLog(eventManagerID, " removeEvent", " eventID: " + eventID + " eventType: " + eventType + " ", serverResponse);
//                break;
//            case MANAGER_LIST_EVENT_AVAILABILITY:
//                eventType = promptForEventType();
//                Logger.clientLog(eventManagerID, " attempting to listEventAvailability");
//                serverResponse = remoteObject.listEventAvailability(eventType);
//                System.out.println(serverResponse);
//                Logger.clientLog(eventManagerID, " listEventAvailability", " eventType: " + eventType + " ", serverResponse);
//                break;
//            case MANAGER_BOOK_EVENT:
//                customerID = askForCustomerIDFromManager(eventManagerID.substring(0, 3));
//                eventType = promptForEventType();
//                eventID = promptForEventID();
//                Logger.clientLog(eventManagerID, " attempting to bookEvent");
//                serverResponse = remoteObject.bookEvent(customerID, eventID, eventType);
//                System.out.println(serverResponse);
//                Logger.clientLog(eventManagerID, " bookEvent", " customerID: " + customerID + " eventID: " + eventID + " eventType: " + eventType + " ", serverResponse);
//                break;
//            case MANAGER_GET_BOOKING_SCHEDULE:
//                customerID = askForCustomerIDFromManager(eventManagerID.substring(0, 3));
//                Logger.clientLog(eventManagerID, " attempting to getBookingSchedule");
//                serverResponse = remoteObject.getBookingSchedule(customerID);
//                System.out.println(serverResponse);
//                Logger.clientLog(eventManagerID, " getBookingSchedule", " customerID: " + customerID + " ", serverResponse);
//                break;
//            case MANAGER_CANCEL_EVENT:
//                customerID = askForCustomerIDFromManager(eventManagerID.substring(0, 3));
//                eventType = promptForEventType();
//                eventID = promptForEventID();
//                Logger.clientLog(eventManagerID, " attempting to cancelEvent");
//                serverResponse = remoteObject.cancelEvent(customerID, eventID, eventType);
//                System.out.println(serverResponse);
//                Logger.clientLog(eventManagerID, " cancelEvent", " customerID: " + customerID + " eventID: " + eventID + " eventType: " + eventType + " ", serverResponse);
//                break;
//            case MANAGER_LOGOUT:
//                repeat = false;
//                Logger.clientLog(eventManagerID, "attempting to Logout");
//                init();
//                break;
        }
        if (repeat) {
            admin(eventManagerID, serverPort);
        }
    }

    private static void printMenu(int userType) {
        System.out.println("*************************************");
        System.out.println("Please choose an option below:");
        if (userType == USER_TYPE_CUSTOMER) {
            System.out.println("1.Book Event");
            System.out.println("2.Get Booking Schedule");
            System.out.println("3.Cancel Event");
            System.out.println("4.Logout");
        } else if (userType == USER_TYPE_ADMIN) {
            System.out.println("1.Add Event");
            System.out.println("2.Remove Event");
            System.out.println("3.List Event Availability");
            System.out.println("4.Book Event");
            System.out.println("5.Get Booking Schedule");
            System.out.println("6.Cancel Event");
            System.out.println("7.Logout");
        }
    }

    private static String promptForMovieType() {
        System.out.println("*************************************");
        System.out.println("Please choose an eventType below:");
        System.out.println("1.Conferences");
        System.out.println("2.Seminars");
        System.out.println("3.Trade Shows");
        switch (input.nextInt()) {
            case 1:
                return MovieModel.AVATAR;
            case 2:
                return MovieModel.AVENGER;
            case 3:
                return MovieModel.TITANIC;
        }
        return promptForMovieType();
    }

    private static String promptForMovieID() {
        System.out.println("*************************************");
        System.out.println("Please enter the EventID (e.g MTLM190120)");
        String movieID = input.next().trim().toUpperCase();
        if (movieID.length() == 10) {
            if (movieID.substring(0, 3).equalsIgnoreCase("ATW") ||
                    movieID.substring(0, 3).equalsIgnoreCase("VER") ||
                    movieID.substring(0, 3).equalsIgnoreCase("OUT")) {
                if (movieID.substring(3, 4).equalsIgnoreCase("M") ||
                        movieID.substring(3, 4).equalsIgnoreCase("A") ||
                        movieID.substring(3, 4).equalsIgnoreCase("E")) {
                    return movieID;
                }
            }
        }
        return promptForMovieID();
    }

    private static int promptForCapacity() {
        System.out.println("*************************************");
        System.out.println("Please enter the booking capacity:");
        return input.nextInt();
    }

}

