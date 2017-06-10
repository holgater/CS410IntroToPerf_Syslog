import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;


public class Client implements Runnable {
    //constructor variables
    String clientName;
    int numPacket;
    int packetSize;
    boolean done=false;

    public Client()
    {
        this.clientName = "Generic log entry";
        this.numPacket = 50;
        this.packetSize = 500;

    }

    public Client(String clientName, int numPacket, int packetSize)
    {
        this.clientName = clientName;
        this.numPacket = numPacket;
        this.packetSize = packetSize;
    }

    public boolean GetDone()
    {
        return this.done;
    }


    @Override
    public void run() {

        
        //set up for local user input/output
        //Scanner reader = new Scanner(System.in);

        //set up connection
        System.out.println("CLIENT: Connecting...");
        //setup socket and input/output streams
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 9001);
            System.out.println("CLIENT: Socket created.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //set up connection
        ObjectOutputStream outStream;
        try {
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("CLIENT: OutputStream created.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //set up connection
        ObjectInputStream inStream;
        try {
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("CLIENT: InputStream created.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //set up connection
        System.out.println("CLIENT: Connection established.");


        //setup the display from inStream [joe test]
        //Displayer display = new Displayer(inStream);
        //run displayer in separate thread so that incoming messages don't block use
        //Thread thread = new Thread(display);
        //thread.start();
        //load up packets string with dumby data
        String packetString=this.clientName;
        //subtract size of including clientName
        int packetNameSize=this.clientName.length();
        int j=0;
        System.out.println("CLIENT: building packet size up." + (this.packetSize-packetNameSize));
        while (j < (this.packetSize-packetNameSize))
        {
            packetString+="a";
            j++;
        }
        System.out.println("CLIENT: starting to send " + this.numPacket + " packet(s).");
        int i = 0;
        //repeatedly send packets
        while (i < this.numPacket) {
            try{
                Thread.sleep((int)getNextRandom()*1); //fire an event in an range from 1 to about X millisecond
            }catch(InterruptedException e){
                System.out.println(e);
            }

            System.out.println("sent: " + i);
            Packet pkt = new Packet();
            pkt.SetInit(System.currentTimeMillis());
            //load up dumby size (bunch of "a"'s)
            pkt.SetData(packetString);
            //package properly and send to output stream
            sendPkt(pkt, outStream);
            ++i;
        } //end while
        System.out.println("CLIENT: Packets specified were sent to server, so time to die.");

        ///alert log client is done sending packets
        this.done=true;
        Packet pkt = new Packet();
        pkt.SetInit(System.currentTimeMillis());
        pkt.SetData("STOP");
        sendPkt(pkt, outStream);

        return;
        //while(true) {
        //
        //}
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

    //get random number from 0 to MAX in a exponential distributed way.
    static private double getNextRandom() {
        Random rand = new Random();
        return  Math.log(1-rand.nextDouble())/(-2);
    }

}
