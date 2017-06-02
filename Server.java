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
