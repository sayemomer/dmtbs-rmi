package client;
import remoteInterface.Calculator;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // look up the remote object in the RMI registry
            Calculator calc = (Calculator) Naming.lookup("//localhost/CalculatorService");

            // call the remote methods
            int result1 = calc.add(5, 3);
            System.out.println("5 + 3 = " + result1);

            int result2 = calc.subtract(5, 3);
            System.out.println("5 - 3 = " + result2);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

