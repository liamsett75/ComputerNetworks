import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.nio.ch.SocketAdaptor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Tests {

// Tests the ArrayList of usernames to make sure it contains the desired info
    @Test
        public void testArrayListUsers (){

        ClientHandler ch = new ClientHandler(null, "Client 0", null, null);
        Server.userNames.add(ch.getUsername());

        if (Server.userNames.contains("Client 0")){
            System.out.println("success");
        }
    }

    // Tests the "!changeUsername" command to confirm if name change worked
    @Test
        public void testChangeName() {

        ClientHandler newCH = new ClientHandler(null, "Client 0", null, null);
        String msg;
        msg =("!changeUsername>bob");
        if (msg.equals("!changeUsername>bob")) {

            newCH.username = "bob";

            System.out.println(newCH.username);

        }
    }
}
