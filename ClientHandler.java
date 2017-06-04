import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.time.LocalTime;


public class ClientHandler {
	//queue access need to be synchronized
	LinkedList<Packet> queue = new LinkedList<Packet>();


	//set arrival time
	public synchronized void addToQueue(Packet pktIn) {
		pktIn.SetArrivalTime(LocalTime.now());
		queue.add(pktIn);
		System.out.println("Packet added to queue");
	}

	public synchronized Packet deQueue(){
		System.out.println("Packet out of queue");
		return queue.poll();
	}

	public synchronized int size(){
		return queue.size();
	}
}
