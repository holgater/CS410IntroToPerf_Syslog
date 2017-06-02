//This class polls front of the queue and log it to the target file.

import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;


public class Log implements Runnable{
    private LinkedList<Packet> queue = new LinkedList<Packet>();
    private int averageProcessTime;
    private static final String FILENAME = "logFile.txt";

    public Log(LinkedList<Packet> q){
        queue = q;
    }

	//Log packets to file X if queue is not empty
	public void run(){
        try (FileWriter file = new FileWriter(FILENAME)){
    		while(true){
    			try{
    				Thread.sleep(100);
    				if(queue.isEmpty() == false){
                        Packet dequeuePacket = queue.poll();
                        file.write(dequeuePacket.GetData()+"\n");
                        //writer.close();
    				}
    			} catch(InterruptedException e){
                    e.printStackTrace();
                }
    		}
        }catch(IOException e){
            e.printStackTrace();
        }
	}
}
