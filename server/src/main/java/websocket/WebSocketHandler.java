package websocket;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import models.AuthData;
import models.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import websocket.commands.UserGameCommand;

import java.io.IOException;

public class WebSocketHandler {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final DataAccess dataAccess;

    private final Gson gson = new Gson();

    public WebSocketHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand cmd = gson.fromJson(message, UserGameCommand.class);

        AuthData authData;
        GameData gameData;

        try {
            authData = dataAccess.getAuth(cmd.getAuthToken());
            gameData = dataAccess.getGame(cmd.getGameID());

        } catch (DataAccessException e) {
            connectionManager.sendError(session, "Error: server error:" + e.getMessage());
        }
    }

}
