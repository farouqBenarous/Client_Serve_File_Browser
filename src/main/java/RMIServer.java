//This class sets up the server.

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.Scanner;

public class RMIServer implements Serializable{



    public static void main(String[] args) {
        String portnumber;
        String start;
        Scanner sc = new Scanner(System.in) ;
        String welcome ="+-----------------------------------------------------+\n" +
                "|                                                     |\n" +
                "|        welcome TO  RMI Browser Application          |\n" +
                "|                                                     |\n" +
                "|  Author : Benarous ahmed omar Farouq                |\n" +
                "|                                                     |\n" +
                "+-----------------------------------------------------+\n" ;

        try{
            System.out.println(welcome);

            // ask for the 'start ' command
            System.out.println("\n Write 'start' and press on enter to run the server  \n");
            start =sc .nextLine();

            while (!start.equals("start"))
            {

                System.out.println("\n Wrong command "+start +"\n");
                System.out.println("\n please Write 'start' and press on enter to run the server  \n");
                start =sc .nextLine();

            }


            // ask for the prot number
            System.out.println("\n Write the port number and press on enter to run the server exmp : 1099  \n");
            portnumber =sc .nextLine();

            while (!isNumber(portnumber))
            {

                System.out.println("\n Wrong  port number exmp : 1099");
                System.out.println("\n Write the port number and press on enter to run the server exmp : 1099  \n");
                portnumber =sc .nextLine();

            }






               // Start the server
            Registry reg = LocateRegistry.createRegistry(Integer.parseInt(portnumber));


            // The location of  the server files  'IMPORTANT CHANGE IT IF YOU DO NOT HAVE THIS PATH '
            String serverpath ="/home/benarousfarouk/Desktop/OISD_Distributed_systesms/serverrmi";

            RmiImplementation imp =  new RmiImplementation(serverpath);
            reg.bind("remoteObject", imp);

            System.out.println("\nServer files location  : "+ serverpath +"\n");
            System.out.println("\nServer name on the registry : 'remoteObject' \n");
            System.out.println("Server is READY  to use  "+" Running On the port number : "+portnumber+"\n");
        }

        catch(Exception e){
            System.out.println("Server failed: Exeption  : " + e);
        }
    }

    // used to check the port number when the user set it
    public static boolean isNumber(String str){
        return str.replaceAll("[0-9]","").length() == 0;
    }


}
