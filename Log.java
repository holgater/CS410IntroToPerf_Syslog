//This class polls front of the queue and log it to the target file.

import java.util.LinkedList;

public class Log implements Runnable{
    private LinkedList<Packet> queue = new LinkedList<Packet>();


    public Log(LinkedList<Packet> q){
        queue = q;
    }

	//Log packets to file X if queue is not empty
	public void run(){
		while(true){
			try{
				Thread.sleep(100);
				if(queue.isEmpty() == false){
				    System.out.println(queue.poll().GetData());
				}
			}catch(InterruptedException e){
				System.out.println(e);
			}

		}
	}
}
