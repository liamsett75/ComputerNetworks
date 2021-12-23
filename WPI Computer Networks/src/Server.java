import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();

    // List that holds the usernames of all online clients
    public static ArrayList<String> userNames = new ArrayList<String>();

    // Counter to give each initial client a number associated with it
    static int i = 0;

    // Announcements made from the server to all clients
    static String notification;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1);
        Socket socket;

        while (true) {

                socket = server.accept();

                System.out.println("Connection to server successful");

                // This is what is read from the server
                DataInputStream dStreamIn = new DataInputStream(socket.getInputStream());
                // This is what is written to the clientHandler from the server
                DataOutputStream dStreamOut = new DataOutputStream(socket.getOutputStream());

                //InputStreamReader streamReadIn = new InputStreamReader(System.in);
                //BufferedReader buffRead = new BufferedReader(streamReadIn);

                ClientHandler newClientHandler = new ClientHandler(socket, "Client " + i, dStreamIn, dStreamOut);

                //Creates and starts a thread for each new clientHandler
                Thread clientThread = new Thread(newClientHandler);
                clientThread.start();

                // This adds the clientHandler to the ArrayList of clients
                clientList.add(newClientHandler);
                // This adds the clientHandler's username (String) to the ArrayList of string
                userNames.add(newClientHandler.username);
                // Notifies all clients of other clients disconnections from the server
                notification = (newClientHandler.getUsername() + " disconnected from the chat room");

                System.out.println("New client " +  newClientHandler.getUsername() + " was added to online user list");

                // counter for each unique client
                i++;

                System.out.println(clientList);
                System.out.println(userNames);


        }
    }
}
