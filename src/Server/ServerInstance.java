package Server;

import ServerInterface.MovieManagement;
import Client.Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerInstance {

    private String serverID;
    private String serverName;
    private int serverRegistryPort;
    private int serverUdpPort;

    public ServerInstance(String serverID) throws Exception {
        this.serverID = serverID;
        switch (serverID){
            case "ATW":
                //why not using this ??
                serverName = MovieManagement.MOVIE_SERVER_ATWATER;
                serverRegistryPort = Client.SERVER_ATWATER;
                serverUdpPort = MovieManagement.Atwater_Server_Port;
                break;
            case "VER":
                serverName = MovieManagement.MOVIE_SERVER_VERDUN;
                serverRegistryPort = Client.SERVER_VERDUN;
                serverUdpPort = MovieManagement.Verdun_Server_Port;
                break;
            case "OUT":
                serverName = MovieManagement.MOVIE_SERVER_OUTRAMONT;
                serverRegistryPort = Client.SERVER_OUTRAMONT;
                serverUdpPort = MovieManagement.Outramont_Server_Port;
                break;
        }
        MovieManagement remoteObject = new MovieManagement(serverID,serverName);
        Registry registry = LocateRegistry.createRegistry(serverRegistryPort);

        registry.bind(Client.MOVIE_MANAGEMENT_REGISTERED_NAME,remoteObject);
        System.out.println(serverID + " Server is Up & Running" + "at Registry port" + serverRegistryPort + "at UDP port" + serverUdpPort);

        //TODO: logger
        //TODO: testDATA
        //TODO: Thread
        //TODO: Listen

    }


}
