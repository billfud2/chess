package facade;
import com.google.gson.Gson;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient {
    static WSClient instance;
    static String websiteURL;
    static Gson gson = new Gson();
    public static String authLog;
    public Session session;
    public WSClient(String URL, int port) throws Exception {
        websiteURL = "ws://" + URL + ":" + Integer.toString(port);
        URI uri = new URI(websiteURL + "/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

}
