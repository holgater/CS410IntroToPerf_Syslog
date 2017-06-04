//This class polls front of the queue and log it to the target file.

import java.util.LinkedList;
import java.time.*;

public class Log implements Runnable{
    private LinkedList<Packet> queue = new LinkedList<Packet>();
    private ClientHandler handler;
    private boolean graceful=true;
    public Log(ClientHandler q){
        
        queue = q.queue;
        handler=q;
        //queue = q;
    }

	//Log packets to file X if queue is not empty
	public void run(){
		while(graceful==true){
            
			try{
				Thread.sleep(100);
				if(queue.isEmpty() == false){
                    System.out.println("log run");
                    System.out.println(LocalTime.now());
				    //System.out.println(queue.poll().GetData());
                    System.out.println(handler.GetClient());
                    if (queue.poll().GetData().equals("STOP"))
                    {
                        System.out.println("We need to subtract a client.");
                        handler.SubtractClient();
                        System.out.println("reminaing clients: " + handler.GetClient());
                    }
                    if (handler.GetClient()==0)
                    {
                        System.out.println("At this time all packets should be done sending and the log has gotten all of them... so we breakup of this loop gracefully..");
                        graceful=false;
                    }
				}
			}catch(InterruptedException e){
				System.out.println(e);
			}
            
		}
        System.out.println("LOG: we can now perform work on log data, inclduing writing to file or outputting metrics..");
	}
}
