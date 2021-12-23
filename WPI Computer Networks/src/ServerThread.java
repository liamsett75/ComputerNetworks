//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ServerThread {
//
//    public static void main(String args[]){
//
//        ServerSocket serverSocket = null;
//        Socket socket = null;
//
//        try{
//            serverSocket = new ServerSocket(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    while (true) {
//        try{
//            socket = serverSocket.accept();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        new ClientThread(socket).start(
//);
////    }
////    }
//}
