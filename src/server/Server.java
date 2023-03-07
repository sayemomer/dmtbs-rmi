package server;

import remoteInterface.Calculator;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            // create the RMI registry
            LocateRegistry.createRegistry(1099);

            // create the remote object
            Calculator calc = new CalculatorImpl();

            // register the remote object with the RMI registry
            Naming.rebind("//localhost/CalculatorService", calc);

            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
