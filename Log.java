//This class polls front of the queue and log it to the target file.
//and keeps track of averageLogTime and averageQueueTime
import java.time.*;
import java.util.*;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;




public class Log implements Runnable{
    private ClientHandler ch;
    private long averageQueueTime = 0;
    private long averageLogTime = 0;
    private boolean graceful=true;
    private static final String FILENAME = "logFile.txt";
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);



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
        int totalQueueLength = 0;
        int numberQueued = 0;
        int numberLogged = 0;
        int dequeued = 0;
        int maxQueueLength = 0;

        int averageQueueLength = 0;

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

    		while(graceful==true){

                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

        		if(ch.size() > 0){
                    Packet dequeuePacket = ch.deQueue();
                    dequeued++;
                    System.out.println("Dequeued: "+dequeued);
                    dequeuePacket.SetProcessTime(System.currentTimeMillis());
                    //update averageQueueTime
                    long thisInQueueTime = inQTime(dequeuePacket);
                    totalQueueTime += thisInQueueTime;
                    ++numberQueued;
                    averageQueueTime = totalQueueTime / numberQueued;
                    System.out.println("averageQueueTime: "+averageQueueTime);

                    //update averageQueueLength
                    totalQueueLength += ch.size();
                    averageQueueLength = totalQueueLength / dequeued;

                    //update Max queueLength
                    if(ch.size() > maxQueueLength){
                        maxQueueLength = ch.size();
                    }


                    long beforeLog = System.currentTimeMillis();
                    bw.write(dequeuePacket.GetData()+", "+
                                        simpleDateFormat.format(new Date(dequeuePacket.GetInit()))+", "+
                                            simpleDateFormat.format(new Date(dequeuePacket.GetArrivalTime()))+", "+
                                                simpleDateFormat.format(new Date(dequeuePacket.GetProcessTime()))+"\n");
                    bw.flush();
                    //bw.write("\t"+ch.size()+"\n");
                    long thisLogTime = System.currentTimeMillis() - beforeLog;
                    //update averageLogTime
                    numberLogged++;
                    totalLogTime += thisLogTime;
                    averageLogTime = totalLogTime / numberLogged;
                    System.out.println("averageLogTime: "+averageLogTime);

                    System.out.println(ch.GetClient());
                    if (dequeuePacket.GetData().equals("STOP"))
                    {
                        System.out.println("We need to subtract a client.");
                        ch.SubtractClient();
                        System.out.println("reminaing clients: " + ch.GetClient());
                    }
                    if (ch.GetClient()==0)
                    {
                        System.out.println("At this time all packets should be done sending and the log has gotten all of them... so we breakup of this loop gracefully..");
                        graceful=false;

                        //write average to end of logfile
                        bw.write("averageQueueTime: " + averageQueueTime + " millisecond\n");
                        bw.write("averageLogTime: " + averageLogTime + " millisecond\n");
                        bw.write("maxQueueLength: " + maxQueueLength + "\n");
                        bw.write("averageQueueLength: " + averageQueueLength + "\n");
                        bw.flush();
                    }
        		}

            }
            //System.out.println("LOG: we can now perform work on log data, inclduing writing to file or outputting metrics..");
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
        return ( p.GetProcessTime() - p.GetArrivalTime() );
    }

    public long getAverageQueueTime(){
        return averageQueueTime;
    }

    public long getAverageLogTime(){
        return averageLogTime;
    }
}
