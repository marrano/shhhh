package org.stefano;

/* ChatClient.java */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
 
import java.net.Socket;
import java.net.InetAddress;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.Random;
 
//public class ChatClient {
// 
//    public static void main (String[] args) {  
//	try {
//	new Client();
//	} catch (Exception e) {
//            System.out.println("\n...ahia, qualcosa non funziona. Chiama il tecnico!\t  :(\n"); 
//            System.exit(1);
//        }
//    }
//}

class Client 
{    
    private static int port = 1001; /* port to connect to */
    private static String hostname = "zittoemosca.no-ip.org"; /* host to connect to */
    
    //private static BufferedReader stdIn;
 
    private static String nick;
    private Socket server;
    private PrintWriter out;
   // private BufferedReader in;
    private Shhhh gui;
 

    public Client(Shhhh gui) throws IOException 
    {
    	this.gui = gui;
    	nick = gui.getNick();
    	          
        server = null;
        gui.printConnectionInfo(" 'spetta, provo a collegarmi...");
        try 
        {
		    InetAddress hostIpAddress = InetAddress.getByName(hostname);
		    gui.printConnectionInfo(" server name: " + hostname);
		    gui.printConnectionInfo(" server IP: " + hostIpAddress.getHostAddress());
            server = new Socket(hostIpAddress.getHostAddress(), port);
            
            /* obtain an output stream to the server... */
            out = new PrintWriter(server.getOutputStream(), true);
            /* ... and an input stream */
            //in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            
            out.println("HELO "+  nick );
            
            gui.printConnectionInfo(" ...pronto! :)"); 
            
        } catch (Exception e) 
        {
            //System.err.println(e);
			gui.setError("...ahia, il server non risponde. Chiama il tecnico!\n\n:(", true); 
            //System.exit(1);
        }
    }
    
    public void listen() throws IOException
    {
        
        
//        Random randomGenerator = new Random();
//        int randImg = randomGenerator.nextInt(7);
//        String imgPath = new String ("img/vale_" + randImg + ".jpg");
//		ImgToAscii img = new ImgToAscii(imgPath);        
		
        /* create a thread to asyncronously read messages from the server */
        ServerConn sc = new ServerConn(server, gui);
        Thread t = new Thread(sc);
        t.start();      
    }
    
    public void sendRemoteMsg(String msg)
    {
	      if(msg.equals("") != true) 
	      {
	    	  out.println("MSG "+  nick + " " + msg);
	      }	
    }
}

 
class ServerConn implements Runnable {
    private BufferedReader in = null;
    private Shhhh gui;
    private int run = 1;
 
    public ServerConn(Socket server, Shhhh gui) throws IOException 
    {
        /* obtain an input stream from the server */
        in = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
        this.gui = gui;
    }
 
    public void run() {
        String msgArrived;
        try 
        {
            /* loop reading messages from the server and show them 
             * on stdout */
            while (((msgArrived = in.readLine()) != null) && (run == 1)) {
				if(!msgArrived.startsWith("SERVER: "))
				{
				  gui.reciveRemoteMsg("\r<< " + msgArrived);
		        } 
            }
        } catch (IOException e) 
        {
            System.err.println(e);
        }
    }
    
     public void stopMe() 
     {
    	 run = 0;
     }
} 
//
//
//class ImgToAscii {
//  private static BufferedImage image;
//  private static Color color;
//
//  public ImgToAscii(String imgName) {
//
//    getImage(imgName);
//    
//    String[] asciiChar =  {"#","W","M","B","R","X","V","Y","I","t","i","+","=",";",":",",","."};
//    int index = 0;
//    
//    String output;
//
//    //Getting the image's height and width gives us a range to work with for the 
//    int width 	= image.getWidth();
//    int height 	= image.getHeight();
//
//    for (int i = 0; i < height/4; i++) {
//      for (int j = 0; j < width/4; j++) {
//	// Get the color for each pixel
//	color = new Color(image.getRGB(j*4, i*4));
//	int red = color.getRed();
//	int green = color.getGreen();
//	int blue = color.getBlue();
//	
//	// Use the luminance formula in the project outline to get each
//	// pixel's luminance, aka "brightness"
//	double luminance = (0.3 * red + 0.59 * green + 0.11 * blue) / 255;
//
//	// Set the default output to " " so everything that's not dark
//	// enough to be considered just turns up as blank space
//	output = " ";
//
//// 	// For the lighter pixels, represent them with an "^"
//// 	if (luminance <= 0.75 && luminance > 0.5)
//// 	output = "^";
//// 	// Second lightest pixels get a "G" assigned to them
//// 	if (luminance <= 0.5 && luminance > 0.25)
//// 	output = "G";
//// 	// Darkest pixels get assigned the darkest ASCII character, "@"
//// 	if (luminance <= 0.25)
//// 	output = "@";
//
//	index = (int)(luminance*19);
//	if(index > 16) index = 16;
//	output = asciiChar[index];
//
//	// Print the line of pixels now converted to ASCII characters
//	System.out.print(output + " ");
//      }
//      // Print out a new line for the next line of pixels to be checked
//      System.out.println();
//    }
//  }
//
//
//  // Helper method for getting the image read in from the text file
//  public void getImage(String sourceFile) {
//    try {
//      //System.out.println(sourceFile + "\n");
//      
//      image = ImageIO.read(getClass().getClassLoader().getResource(sourceFile));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//}
