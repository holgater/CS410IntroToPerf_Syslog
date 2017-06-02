import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ClientHandler {

	LinkedList<Packet> queue = new LinkedList<Packet>();
    int clients=0;
    boolean active=false;
    
    public void AddClient()
    {
        clients+=1;
        active=true;
    }
    public void SubtractClient()
    {
        clients-=1;
        active=true;
    }
    
    public int GetClient()
    {
        return this.clients;
    }


	public void addToQueue(Packet pktIn) {
		queue.add(pktIn);
		System.out.println("Packet added to queue");
	}
}
