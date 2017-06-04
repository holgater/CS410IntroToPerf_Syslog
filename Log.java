//This class polls front of the queue and log it to the target file.
//and keeps track of averageLogTime and averageQueueTime
import java.time.*;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;




public class Log implements Runnable{
    private ClientHandler ch;
    private long averageQueueTime;
    private long averageLogTime;
    private static final String FILENAME = "logFile.txt";

    public Log(ClientHandler handlerIn){
        ch = handlerIn;
        averageQueueTime = 0;
        averageLogTime = 0;
    }

	//Log packets to file X if queue is not empty
    //Set processTime, calculate averageQueueTime and averageLogTime in milisecond
	public void run(){
        long totalLogTime = 0;
        long totalQueueTime = 0;
        int numberQueued = 0;
        int numberLogged = 0;
        int dequeued = 0;

        BufferedWriter bw = null;
        FileWriter fw = null;

        try{
            File file = new File(FILENAME);
            // if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

            // true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);


    		while(true){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
    		    if(ch.size() > 0){
                    Packet dequeuePacket = ch.deQueue();
                    dequeued++;
                    System.out.println("Dequeued: "+dequeued);
                    dequeuePacket.SetProcessTime(LocalTime.now());

                    //update averageQueueTime
                    long thisInQueueTime = inQTime(dequeuePacket);
                    totalQueueTime += thisInQueueTime;
                    ++numberQueued;
                    averageQueueTime = totalQueueTime / numberQueued;
                    System.out.println("averageQueueTime: "+averageQueueTime);

                    long beforeLog = System.currentTimeMillis();
                    bw.write(dequeuePacket.GetData()+", "+
                                        dequeuePacket.GetInit()+", "+
                                            dequeuePacket.GetArrivalTime()+", "+
                                                dequeuePacket.GetProcessTime()+"\n");
                    bw.flush();
                    //bw.write("\t"+ch.size()+"\n");
                    long thisLogTime = System.currentTimeMillis() - beforeLog;
                    //update averageLogTime
                    numberLogged++;
                    totalLogTime += thisLogTime;
                    averageLogTime = totalLogTime / numberLogged;
                    System.out.println("averageLogTime: "+averageLogTime);
    			}
    		}
        }catch(IOException e){
            e.printStackTrace();
        }finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

    //get time in milisecond a packet spend in the queue
    public long inQTime(Packet p){
        if(p.GetProcessTime().getNano() < p.GetArrivalTime().getNano()){
            System.out.println("Need to change LocalTime to Epoch time I believe");
            System.out.println(p.GetProcessTime().getNano()+" < ?"+p.GetArrivalTime().getNano());
        }
        return (p.GetProcessTime().getNano() - p.GetArrivalTime().getNano())/1000000;
    }

    public long getAverageQueueTime(){
        return averageQueueTime;
    }

    public long getAverageLogTime(){
        return averageLogTime;
    }
}
