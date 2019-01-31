//This class connects to the server and accepts commands from the user.

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;


public class RMIClient implements Serializable {

    public static void main(String[] args) {

        Properties prop = new Properties();
        InputStream input = null;
        String ipadress;
        String hostname;
        int portnumber;
        String clientpath;
        String serverpath ="/home/";
        String	namedir ;
        String command="";
        String upload = "upload";
        String download = "download";
        String list= "list";
        String mkdir= "mkdir";
        String rmdir= "rmdir";
        String rm= "rm";
        String shutdown= "shutdown";
        String state ="stay";
        String start ="";
        Scanner sc = new Scanner(System.in) ;

        // You should change this path  , just go to gradle/wrapper/gradle.properties and copy the path
        String filename = "/home/benarousfarouk/Desktop/OISD_Distributed_systesms/Assignments/RMI/RMI_Grade_12/RemoteBrowserFinal/gradle/wrapper/gradle.properties";
        String registryAddress ="";




        try {
            String welcome ="+-----------------------------------------------------+\n" +
                    "|                                                     |\n" +
                    "|        welcome TO  RMI Browser Application          |\n" +
                    "|                                                     |\n" +
                    "|  Author : Benarous ahmed omar Farouq                |\n" +
                    "|                                                     |\n" +
                    "+-----------------------------------------------------+\n" ;
            System.out.println(welcome);



            // Get the ip adress  Specified in the argument -pregistryadress = xx.xx
             input = new FileInputStream(filename);
             prop.load(input);
            registryAddress =prop.getProperty("registryAddress");


            //Print it
            ipadress =  registryAddress;
            System.out.println("Ip adress of the server : "+ipadress);

            //Split the registry adress to hostname and port number
            hostname = ipadress.split(":")[0];
            System.out.println("\nHost name : " + hostname);

            portnumber = Integer.parseInt(ipadress.split(":")[1]);
            System.out.println("\nseeking connection on:" +ipadress);

            // Get the registry of the server and connect to it
            Registry myreg = LocateRegistry.getRegistry(hostname,portnumber);
            RmiInterface inter = (RmiInterface) myreg.lookup("remoteObject");
            System.out.println("\n Connected suceessfuly to the server  \n");

            // while the user set a wrong operation or not shutdown the conecctions keep doing this
            while (!command.equals("shutdown")) {

                //  the commands
                System.out.println("\n Operations that you able to do :" +
                        "\n 1- Upload file to the server Write "+upload+
                        "\n" +
                        " 2- Download file from the server Write  "+download+
                        "\n 3- List files in the server Write  "+list+
                        "\n 4- Create file in the server Write "+mkdir+
                        "\n 5- Remove file from the server Write "+rmdir+
                        "\n 6- Shutdown file to the server Write  "+shutdown);
                command =sc .nextLine();


                // From this step it will test every time the command that the user set it
                // we have 6 operations so we have  6 of if statments


                /*-----------------------------------to upload a file -----------------*/
                if (command.equals(upload)) {

                    System.out.println("\n Set the Client path that you want to upload exmp : /home/file.txt ");
                    clientpath = sc.nextLine();

                    System.out.println("\n Set the Server path where  you want to upload you file exmp : /home/file.txt ");
                    serverpath = sc.nextLine();

                    File clientpathfile = new File(clientpath);
                    byte[] mydata = new byte[(int) clientpathfile.length()];
                    FileInputStream in = new FileInputStream(clientpathfile);
                    System.out.println("\nuploading to server...");
                    in.read(mydata, 0, mydata.length);
                    inter.uploadFileToServer(mydata, serverpath, (int) clientpathfile.length());
                    in.close();
                    System.out.println("\n            File  successfully Uploaded  from  the server \n    ");

                    System.out.println("\n1- To ReList the operations write '1'" +
                            "\n2- To shutdown the conecction with the server write '2'");
                    command =sc.nextLine();

                    //to shutdown the client
                    if (command.equals(shutdown)) {
                        System.out.println("\n                You have shutdown  The conection with the server");
                        System.exit(0);
                    }

                }




                /*-----------------------------------to download a file -----------------*/

                if (command.equals(download)) {

                    System.out.println("\n Set the Server path the file the you want to dwonload exmp : /home/file.txt ");
                    serverpath = sc.nextLine();

                    System.out.println("\n Set the Client path Where you want to put you downloaded file exmp : /home/file.txt ");
                    clientpath = sc.nextLine();


                    byte[] mydata = inter.downloadFileFromServer(serverpath);
                    System.out.println("downloading...");
                    File clientpathfile = new File(clientpath);
                    FileOutputStream out = new FileOutputStream(clientpathfile);
                    out.write(mydata);
                    out.flush();
                    out.close();
                    System.out.println("\n             File  successfully Downloaded from  the server  :) ");

                    System.out.println("\n1- To ReList the operations write '1'" +
                            "\n2- To shutdown the conecction with the server write '2'");
                    command =sc.nextLine();

                    //to shutdown the client
                    if (command.equals(shutdown)) {
                        System.out.println("\n                You have shutdown  The conection with the server");
                        System.exit(0);
                    }


                }


                /* ------------------------   to list all the files in a directory ------------------------------- */

                if (command.equals(list)) {
                    String statehere = "";
                    String x="";
                    serverpath = "/home/";

                    String[] filelist = inter.listFiles(serverpath);
                    for (String i : filelist) { System.out.println(i); }

                    System.out.println("\nSet The path tha you want to list (currently in "+serverpath+")");
                    serverpath += sc.nextLine();

                    while (!statehere.equals("exit")) {
                        filelist = inter.listFiles(serverpath);
                        for (String i : filelist) { System.out.println(i); }

                        while (!x.equals("exit")) {
                            System.out.println("1- Set The path that you want to list (currently in " + serverpath + ")" + "\n2-To exit thi command write exit");
                            x = sc.nextLine();
                            serverpath += "/"+x;

                            if (!findString(filelist,x)) {
                                System.out.println("Path not Found ");
                                statehere = "exit";
                                filelist =removeElements(filelist,x);
                            }

                            //	filelist [filelist.length-1]=x;

                            if (x.equals("exit") ) { statehere = "exit"; }
                            break;
                        }

                    }
                    System.out.println("\n1- To ReList the operations write '1'" +
                            "\n2- To shutdown the conecction with the server write '2'");
                    command =sc.nextLine();
                    //to shutdown the client
                    if (command.equals("2")) {
                        System.out.println("\n                You have shutdown  The conection with the server");
                        System.exit(0);
                    }
                }



                /*-----------------------------------to create a new directory -----------------*/

                if (command.equals(mkdir)) {

                    System.out.println("\n Set the name of  the directory that you want to create ");
                    namedir = sc.nextLine();


                    System.out.println("\n Set the path where you want create the directory ");
                    serverpath = sc.nextLine();

                    boolean bool = inter.createDirectory(serverpath+"/"+namedir);

                    if ( bool ) { System.out.println("\n               directory created Successfuly "); }

                    else { System.out.println("\n              Can not Create directory name or path invalide "); }


                    System.out.println("\n1- To ReList the operations write '1'" +
                            "\n2- To shutdown the conecction with the server write '2'");
                    command =sc.nextLine();

                    //to shutdown the client
                    if (command.equals(shutdown)) {
                        System.out.println("\n                You have shutdown  The conection with the server");
                        System.exit(0);
                    }
                }


                /*-----------------------------------to remove/delete a directory -----------------*/

                if (command.equals(rmdir) ) {



                    System.out.println("\n Set the  directory path wich you want Delete  ");
                    serverpath = sc.nextLine();
                    boolean bool = inter.removeDirectoryOrFile(serverpath);

                    if ( bool ) { System.out.println("\n               directory Deleted Successfuly "); }

                    else { System.out.println("\n              Can not Deleted directory  path invalide "); }

                    System.out.println("\n1- To ReList the operations write '1'" +
                            "\n2- To shutdown the conecction with the server write '2'");
                    command =sc.nextLine();

                    //to shutdown the client
                    if (command.equals(shutdown)) {
                        System.out.println("\n                You have shutdown  The conection with the server");
                        System.exit(0);
                    }
                }



                /*-----------------------------------to shutdown the Conecction with the  server  -----------------*/
                if (command.equals(shutdown)) {
                    System.out.println("\n                You have shutdown  The conection with the server");
                    System.exit(0);
                }

            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("error While exucution :  \n Error details  : "+e.toString());
        }
    }

    // method used 'list operation'  to check if the path exist or not when the user set a path
    public static boolean findString(String[] strings, String desired){
        for (String str : strings){
            if (desired.equals(str)) {
                return true;
            }
        }
        return false; //if we get here… there is no desired String, return false.
    }

    // method used 'list operation'  to retriev  when path  does not exist
    public static String[] removeElements(String[] input, String deleteMe) {
        List result = new LinkedList();

        for(String item : input)
            if(!deleteMe.equals(item))
                result.add(item);

        return (String[]) result.toArray(input);
    }


}

