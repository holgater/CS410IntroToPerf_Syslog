import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ClientHandler {

	LinkedList<Packet> queue = new LinkedList<Packet>();

	public void addToQueue(Packet pktIn) {
		queue.add(pktIn);
		System.out.println("Packet added to queue");
	}
}
