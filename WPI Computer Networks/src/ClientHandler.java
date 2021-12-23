import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ClientHandler extends Thread implements Runnable {

    public String getUsername() {
        return username;
    }

    public String username; //This is what the Client will be referenced by
    final DataInputStream dStreamIn; //This is what gets read by the clientHandler
    final DataOutputStream dStreamOut; // This is what gets written to each Clients console
    Socket socket; //This is the socket the Client has opened
    boolean isOnline; //Boolean value that keeps track is clientHandler is online or offline



    public ClientHandler(Socket socket, String username, DataInputStream dStreamIn, DataOutputStream dStreamOut) {
        this.socket = socket;
        this.dStreamIn = dStreamIn;
        this.dStreamOut = dStreamOut;
        this.username = username;
        this.isOnline = true;
    }

    @Override
    public void run() {

        String msgIn;
        //String msgOut;

        while (true) {
            try {
                msgIn = dStreamIn.readUTF();
                System.out.println(msgIn);

                //Disconnects the clientHandler from the server by closing the socket
                //Writes to the server and other clients that clientHandler has disconnected from server
                    if (msgIn.equals("!disconnect")) {
                        System.out.println(username + " disconnecting...");
                        this.isOnline = false;
                        Server.userNames.remove(this.username);
                        this.dStreamOut.writeUTF(Server.notification);
                        this.socket.close();
                        System.out.println(this.username + " disconnected from the chat room");
                        break;

                    }

                //Connects the clientHandler from to the server through desired socket
                //Writes to the server and other clients that clientHandler has connected to server
                    if (msgIn.equals("!connect")) {
                        System.out.println(this.username + " connecting...");
                        ServerSocket newss = new ServerSocket(1);
                        socket = newss.accept();
                        this.isOnline = true;
                        System.out.println(this.username + " Connected to the chat room");
                        break;

                    }

                    // Messages from DataInputStream WITH ">" AND WITHOUT "!" (used for commands)
                    // Direct messaging method that separates InputStream into tokens separated by the delimiter ">".
                    // Separated tokens include the actual message to be send and the recipient who will receive the message
                    if (msgIn.contains(">") && (!msgIn.contains("!"))) {

                        StringTokenizer st = new StringTokenizer(msgIn, ">");
                        String actualMsg = st.nextToken();
                        String recipient = st.nextToken();

                        // Iterates through the Server's clientList until the recipient string matches the clientHandler username string
                        // Will write the output stream to ONLY the recipient if recipient exists and is online
                        for (ClientHandler ch : Server.clientList) {
                            if (ch.username.equals(recipient) && ch.isOnline) {
                                ch.dStreamOut.writeUTF(this.username + " : " + actualMsg + "\n\r");
                                dStreamOut.flush();
                                break;
                            }
                        }
                    }

                // This is for the clientHandler command "!changeUsername"
                else if (msgIn.contains("!changeUsername")){

                    StringTokenizer st = new StringTokenizer(msgIn, "=");
                    String command = st.nextToken();
                    String newName = st.nextToken();

                    // Iterates through the Server's clientList to check and see if username already exists
                    // If the username is taken, Clients username will not be changed
                    // If the username is not taken, the Clients username on the console output will be changed
                    for (ClientHandler ch : Server.clientList) {
                        if(command.equals("!changeUsername") && (!ch.username.equals(newName))) {
                            System.out.println(command);
                            this.username = newName;
                            ch.dStreamOut.writeUTF(newName + " has changed their username");
                            break;
                        }
                        else if  (ch.username.equals(newName)){
                            dStreamOut.writeUTF("Username already taken");
                            break;
                        }

                    }
                }
                // This is for the clientHandler command "!online"
                    else {

                        // If the InputStream entered is "!online", clientHandler will print a list of online Client's usernames to the console
                        for (ClientHandler ch : Server.clientList) {
                            if(msgIn.equals("!online") && ch.isOnline){
                                ch.dStreamOut.writeUTF(Server.userNames.toString());
                            }
                        // If no Client's username is found, OutputStream will write to ALL clients currently online
                        ch.dStreamOut.writeUTF(this.username + " : " + msgIn + "\n\r");
                        ch.dStreamOut.flush();
                        }
                    }

                } catch (IOException e) {
                PrintWriter pw = new PrintWriter(dStreamOut);
                pw.println(dStreamOut);
                    e.printStackTrace();
                }
        }

        try{
            // Closes the InputStream
            this.dStreamIn.close();
            // Closes the OutputStream
            this.dStreamOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
