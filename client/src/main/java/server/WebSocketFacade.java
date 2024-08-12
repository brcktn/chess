package server;

import com.google.gson.Gson;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;

public class WebSocketFacade extends Endpoint {
    Session session;


    public WebSocketFacade(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI uri = new URI(url + "/ws");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    System.out.println(message);
                }
            });


        } catch (URISyntaxException | IOException | DeploymentException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer() throws IOException {

    }

    private void sendMessage(UserGameCommand cmd)  throws IOException {
        session.getBasicRemote().sendText(new Gson().toJson(cmd));
    }
}