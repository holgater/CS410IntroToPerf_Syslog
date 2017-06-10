import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String argv[]) throws Exception {

        
        
    
        System.out.println("Welcome to syslog simulator.");
        System.out.println("1: 5 clients, 500 entries, 500kb each.");
        System.out.println("2: 5 clients, 250 entries, 500kb each.");
        System.out.println("3: 5 clients, 150 entries, 500kb each.");
        System.out.println("4: 5 clients, 50 entries, 500kb each.");
        System.out.println("4: 5 clients, 10 entries, 500kb each.");
        
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number to a simulation you'd like to run: ");
        int simToRun = reader.nextInt(); // Scans the next token of the input as an int.
        System.out.println(simToRun);
        
        //Handles clients
        final ClientHandler clientHandler = new ClientHandler();

        final Log log = new Log(clientHandler);


        //processing packets if queue is not empty
        Thread logThread = new Thread(log);
        logThread.start();

        //setup the sockets
        ServerSocket welcomeSocket = new ServerSocket(9001);
        Socket clientSocket = null;

        if(simToRun==1)
        {
        Client[] clientList = new Client[5];
        clientList[0]=new Client("sim1prog1",500,500);
        clientList[1]=new Client("sim1prog2",500,500);
        clientList[2]=new Client("sim1prog3",500,500);
        clientList[3]=new Client("sim1prog4",500,500);
        clientList[4]=new Client("sim1prog5",500,500);
        for(int i=0; i<clientList.length; i++) {
            Thread threadclient = new Thread(clientList[i]);
            System.out.println("SERVER: Starting up Simulation 1 on thread client " + i);
            threadclient.start();
            }
        }
        else if(simToRun==2){
            Client[] clientList = new Client[5];
            clientList[0]=new Client("sim2prog1",250,500);
            clientList[1]=new Client("sim2prog2",250,500);
            clientList[2]=new Client("sim2prog3",250,500);
            clientList[3]=new Client("sim2prog4",250,500);
            clientList[4]=new Client("sim2prog5",250,500);
            for(int i=0; i<clientList.length; i++) {
                Thread threadclient = new Thread(clientList[i]);
                System.out.println("SERVER: Starting up Simulation 2 on thread client " + i);
                threadclient.start();
            }
        }
        else if(simToRun==3){
            Client[] clientList = new Client[5];
            clientList[0]=new Client("sim3prog1",150,500);
            clientList[1]=new Client("sim3prog2",150,500);
            clientList[2]=new Client("sim3prog3",150,500);
            clientList[3]=new Client("sim3prog4",150,500);
            clientList[4]=new Client("sim3prog5",150,500);
            for(int i=0; i<clientList.length; i++) {
                Thread threadclient = new Thread(clientList[i]);
                System.out.println("SERVER: Starting up Simulation 3 on thread client " + i);
                threadclient.start();
            }
        }
        else if(simToRun==4){
            Client[] clientList = new Client[5];
            clientList[0]=new Client("sim4prog1",50,500);
            clientList[1]=new Client("sim4prog2",50,500);
            clientList[2]=new Client("sim4prog3",50,500);
            clientList[3]=new Client("sim4prog4",50,500);
            clientList[4]=new Client("sim4prog5",50,500);
            for(int i=0; i<clientList.length; i++) {
                Thread threadclient = new Thread(clientList[i]);
                System.out.println("SERVER: Starting up Simulation 4 on thread client " + i);
                threadclient.start();
            }
        }
        else if(simToRun==5){
            Client[] clientList = new Client[5];
            clientList[0]=new Client("sim5prog1",10,500);
            clientList[1]=new Client("sim5prog2",10,500);
            clientList[2]=new Client("sim5prog3",10,500);
            clientList[3]=new Client("sim5prog4",10,500);
            clientList[4]=new Client("sim5prog5",10,500);
            for(int i=0; i<clientList.length; i++) {
                Thread threadclient = new Thread(clientList[i]);
                System.out.println("SERVER: Starting up Simulation 5 on thread client " + i);
                threadclient.start();
            }
        }
        else
        {
            System.out.println("No simulation selected 1-5, so we end.");
            return;
        }
        
        
        System.out.println("SERVER: Now we move forward to listen for client(s)..");


        while(true)	{

            //if(client.GetDone()==true)
            //{
            //    System.out.println("exit");
            //    break;
            //}

            //Connect new clients
            clientSocket = welcomeSocket.accept();
                        System.out.println("loop2");
            System.out.println("SERVER: New client connection");

            //Create service to handle client
            ClientService clientService = new ClientService(clientSocket, clientHandler);

            //Create new thread to run service
            Thread thread = new Thread(clientService);
            thread.start();

        }

    } //end main

}
