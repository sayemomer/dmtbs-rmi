package ServerInterface;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Interface.MovieManagementInterface;

public class MovieManagement extends UnicastRemoteObject implements MovieManagementInterface {

    private String serverID;
    private String serverName;

    public static final int Atwater_Server_Port = 8888;
    public static final int Verdun_Server_Port = 7777;
    public static final int Outramont_Server_Port = 6666;
    public static final String MOVIE_SERVER_ATWATER = "ATW";
    public static final String MOVIE_SERVER_VERDUN = "VER";
    public static final String MOVIE_SERVER_OUTRAMONT = "OUT";

    public MovieManagement(String serverID, String serverName) throws RemoteException {
        super();
        this.serverID = serverID;
        this.serverName = serverName;
    }

    @Override
    public String addEvent(String eventID, String eventType, int bookingCapacity) throws RemoteException {
        return null;
    }

    @Override
    public String removeEvent(String eventID, String eventType) throws RemoteException {
        return null;
    }

    @Override
    public String listEventAvailability(String eventType) throws RemoteException {
        return null;
    }

    @Override
    public String bookEvent(String customerID, String eventID, String eventType) throws RemoteException {
        return null;
    }

    @Override
    public String getBookingSchedule(String customerID) throws RemoteException {
        return null;
    }

    @Override
    public String cancelEvent(String customerID, String eventID, String eventType) throws RemoteException {
        return null;
    }
}
