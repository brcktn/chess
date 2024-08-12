package websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import models.AuthData;
import models.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
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

            if (authData == null) {
                connectionManager.sendError(session, "Error: invalid auth");
                return;
            }

            if (gameData == null) {
                connectionManager.sendError(session, "Error: invalid gameID");
                return;
            }

        } catch (DataAccessException e) {
            connectionManager.sendError(session, "Error: server error:" + e.getMessage());
            return;
        }

        switch (cmd.getCommandType()) {
            case CONNECT -> connect(session, authData.username(), cmd.getPlayerColor(), gameData);
            case MAKE_MOVE -> makeMove(session, authData.username(), cmd.getMove(), gameData);
            case LEAVE -> leave(session, authData.username(), gameData);
            case RESIGN -> resign(session, authData.username(), gameData);
        }
    }

    private void connect(Session session, String username, ChessGame.TeamColor teamColor, GameData gameData) throws IOException {
        if (teamColor == null) {
            connectObserver(session, username, gameData);
            return;
        }

        connectionManager.addSession(gameData.gameID(), session);

        String gameJson = gson.toJson(new ServerMessage(gameData.game(), teamColor));
        connectionManager.send(session, gameJson);

        String notificationJson = gson.toJson(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, username +
                " joined game as " + (teamColor == ChessGame.TeamColor.BLACK ? "black" : "white")));
        connectionManager.broadcast(gameData.gameID(), notificationJson);
    }

    private void makeMove(Session session, String username, ChessMove move, GameData gameData) {

    }

    private void leave(Session session, String username, GameData gameData) {

    }

    private void resign(Session session, String username, GameData gameData) {

    }

    private void connectObserver(Session session, String username, GameData gameData) throws IOException {
        connectionManager.addSession(gameData.gameID(), session);

        String gameJson = gson.toJson(new ServerMessage(gameData.game(), ChessGame.TeamColor.WHITE));
        connectionManager.send(session, gameJson);

        String notificationJson = gson.toJson(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                username + " is observing"));
        connectionManager.broadcast(gameData.gameID(), notificationJson);
    }

}