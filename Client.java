import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Client {
	
	public static void main(String argv[]) throws Exception {
		
		//set up for local user input/output 
		Scanner reader = new Scanner(System.in);
		
		//set up connection
		System.out.println("CLIENT: Connecting...");
		//setup socket and input/output streams
		Socket clientSocket = new Socket("localhost", 9001);
		ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("CLIENT: Connection established");
		
		
		//setup the display from inStream
		//Displayer display = new Displayer(inStream);
		//run displayer in separate thread so that incoming messages don't block use
		//Thread thread = new Thread(display);
		//thread.start();
		int i = 0;
		//repeatedly send packets
		while (i < 10) {
			System.out.println("sent: " + i);
			Packet pkt = new Packet(LocalTime.now(), "Some string that takes up some space " + i);

			//package properly and send to output stream
			sendPkt(pkt, outStream);
			++i;
		} //end while
		while(true) {
			
		}
	} //end main

	//handle the command and output the appropriate packet
	private static void sendPkt(Packet packetOut, ObjectOutputStream outStream) {
		try {
			outStream.writeObject(packetOut);
			outStream.flush();
			outStream.reset();
		} catch (IOException e) {
			System.out.println("CLIENT: [ERROR] IOException while sendng message command");
			//e.printStackTrace();
		}
		
		return;
	} //end send packet

}
