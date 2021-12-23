import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    @FXML
    static TextArea txtArea;
    @FXML
    static FXMLLoader loader;
    @FXML
    static Parent root;

    @Override
    public void start(Stage primaryStage) {

//        loader = new FXMLLoader(getClass().getResource("chatRoomScene.fxml"));
//        try{
//            root = loader.load();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
////
//        Server server = new Server();
//        Client client = new Client();
//        Client2 client2 = new Client2();
//        Client3 client3 = new Client3();
//
//        server.main(args);
//        client.main(args);
//        client2.main(args);
//        client3.main(args);
    }

    public static void main (String[] args) throws IOException {
        launch(args);

    }
}
