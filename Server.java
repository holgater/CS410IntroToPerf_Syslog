import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

	public static void main(String argv[]) throws Exception {

		//Handles clients
		final ClientHandler clientHandler = new ClientHandler();

		final Log log = new Log(clientHandler.queue);
		
		//processing packets if queue is not empty
		Thread logThread = new Thread(log);
		logThread.start();

		//setup the sockets
		ServerSocket welcomeSocket = new ServerSocket(9001);
		Socket clientSocket = null;

		while(true)	{
			//Connect new clients
			clientSocket = welcomeSocket.accept();
			System.out.println("SERVER: New client connection");

			//Create service to handle client
			ClientService clientService = new ClientService(clientSocket, clientHandler);

			//Create new thread to run service
			Thread thread = new Thread(clientService);
			thread.start();

		}

	} //end main

}


import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    
    public static void main(String argv[]) throws Exception {
        
        //Handles clients
        final ClientHandler clientHandler = new ClientHandler();
        
        final Log log = new Log(clientHandler.queue);
        
        //processing packets if queue is not empty
        Thread logThread = new Thread(log);
        logThread.start();
        
        //setup the sockets
        ServerSocket welcomeSocket = new ServerSocket(9001);
        Socket clientSocket = null;
        
        //run a client
        Client client = new Client("prog1",200,1000);
        Thread threadclient = new Thread(client);
        System.out.println("SERVER: Starting up a thread client #1.");
        threadclient.start();
        
        
        //run a client
        Client client2 = new Client("prog2",200,1000);
        Thread threadclient2 = new Thread(client2);
        System.out.println("SERVER: Starting up a thread client #2.");
        threadclient2.start();
        
        
        System.out.println("SERVER: Now we move forward to listen for client(s)..");
        
        
        while(true)	{
            //Connect new clients
            clientSocket = welcomeSocket.accept();
            System.out.println("SERVER: New client connection");
            
            //Create service to handle client
            ClientService clientService = new ClientService(clientSocket, clientHandler);
            
            //Create new thread to run service
            Thread thread = new Thread(clientService);
            thread.start();
            
        }
        
    } //end main
    
}
