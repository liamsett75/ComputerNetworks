import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.net.*;
import java.util.Scanner;

public class Client2 extends Thread implements Runnable{


   public static void main(String[] args) throws IOException {

      //launch(args); (Originally used to launch fxml file)

      // Takes user input from the console
      Scanner scanner = new Scanner(System.in);

      //"localhost" = 127.0.0.1 ip which is used for ip address
      InetAddress ip = InetAddress.getByName("localhost");
      System.out.println("You have connected to the chatroom");

      // assigns the socket to have localhost ip and allow connections through port 1
      Socket socket = new Socket(ip, 1);

      //InputStreamReader streamReadIn = new InputStreamReader(System.in);
      //BufferedReader buffRead = new BufferedReader(streamReadIn);

      // This is what is read from the Client
      DataOutputStream dStreamOut = new DataOutputStream(socket.getOutputStream());
      // This is what is written from the Client to the clientHandler
      DataInputStream dStreamIn = new DataInputStream(socket.getInputStream());

      // Thread is created for sending and receiving messages
      // sendMessage writes the message to the DataOutputStream
      Thread sendMessage = new Thread(new Runnable() {
         @Override
         public void run() {
            while (true) {

               String msg = scanner.nextLine();
               try {
                  dStreamOut.writeUTF(msg);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
      });

      // readMessage reads the message from the DataInputStream
      Thread readMessage = new Thread(new Runnable() {
         @Override
         public void run() {

            // all DataStreamInputs will be read unless "!disconnect" is typed causing client to disconnect
            while (!dStreamIn.toString().equals("!disconnect")) {
               try {
                  String msg = dStreamIn.readUTF();
                  System.out.println(msg);
               } catch (IOException e) {
                  System.out.println("Disconnecting...");
                  try {
                     //permanently terminates connection to server and closes socket
                     socket.close();
                  } catch (IOException e1) {
                     e1.printStackTrace();
                  }
                  System.out.println("You disconnected from the chat room");
                  break;
               }
            }
         }
      });

      //Starts the readMessage thread
      readMessage.start();
      //Starts the sendMessage thread
      sendMessage.start();
   }


}
