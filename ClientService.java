import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.LinkedList;

public class ClientService implements Runnable {

	//local variables
	private Socket socket;
	private ClientHandler clientHandler;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;

    boolean graceful=true;

	//Constructor
	public ClientService(Socket socketIn, ClientHandler handlerIn)  {
		socket = socketIn;
		clientHandler = handlerIn;
	}

	@Override
	public void run() {
		//run the client service
        clientHandler.AddClient();
		try {
			try {

				//create new input/output streams
				inStream = new ObjectInputStream(socket.getInputStream());
				outStream = new ObjectOutputStream(socket.getOutputStream());
				while(this.graceful==true) {
					//write back
					//Packet response = new Packet();
					//response.SetInit(LocalTime.now());
					//response.SetData("Response");
					//outStream.writeObject(response);
					//outStream.flush();
					//outStream.reset();
					//service the client
					serviceClient();
				}
			} finally {
				//close the socket
                System.out.println("we are done.");
				socket.close();


			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void serviceClient() throws IOException {
		while (true) {

			Packet pktIn;
			//try to get incoming packet
			try {
				pktIn = (Packet) inStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return;
			}
			//actually handle the packet
			handlePacket(pktIn);
		}

	}

	private void handlePacket(Packet pktIn) {
        if(pktIn.GetData().equals("STOP"))
        {
                    //clientHandler.SubtractClient();
                    System.out.println("CLIENTSERVICE: were done sending packets");
                    this.graceful=false;
        }
		System.out.println("Init Time: " + pktIn.GetInit() + " - Data: " + pktIn.GetData());
		clientHandler.addToQueue(pktIn);

	} //end handlePacket()
}
