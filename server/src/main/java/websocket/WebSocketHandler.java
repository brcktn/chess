package websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
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
                connectionManager.sendError(session, "invalid auth");
                return;
            }

            if (gameData == null) {
                connectionManager.sendError(session, "invalid gameID");
                return;
            }

        } catch (DataAccessException e) {
            connectionManager.sendError(session, "server error:" + e.getMessage());
            return;
        }

        switch (cmd.getCommandType()) {
            case CONNECT -> connect(session, authData.username(), gameData);
            case MAKE_MOVE -> makeMove(session, authData.username(), cmd.getMove(), gameData);
            case LEAVE -> leave(session, authData.username(), gameData);
            case RESIGN -> resign(session, authData.username(), gameData);
        }
    }

    private void connect(Session session, String username, GameData gameData) throws IOException {

        connectionManager.addSession(gameData.gameID(), session);

        String gameJson = gson.toJson(new ServerMessage(gameData.game()));
        connectionManager.send(session, gameJson);

        String notificationJson = gson.toJson(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, username +
                " joined game"));
        connectionManager.broadcast(gameData.gameID(), notificationJson, session);
    }

    private void makeMove(Session session, String username, ChessMove move, GameData gameData) throws IOException {
        if (move == null) {
            connectionManager.sendError(session, "No move provided");
            return;
        }

        ChessGame.TeamColor currentColor = null;

        if (username.equals(gameData.whiteUsername())) {
            currentColor = ChessGame.TeamColor.WHITE;
        }
        if (username.equals(gameData.blackUsername())) {
            currentColor = ChessGame.TeamColor.BLACK;
        }
        if (currentColor == null) {
            connectionManager.sendError(session, "You're not playing in this game!");
            return;
        }

        if (gameData.game().getGameOver()) {
            connectionManager.sendError(session, "Game is over");
            return;
        }

        ChessGame game = gameData.game();
        if (currentColor != gameData.game().getTeamTurn()) {
            connectionManager.sendError(session, "It's not your turn!");
            return;
        }

        try {
            game.makeMove(move);
        } catch (InvalidMoveException e) {
            connectionManager.sendError(session, "Invalid move!");
            return;
        }

        Gson gson = new Gson();
        if (game.isInCheckmate((game.getTeamTurn()))) {
            game.setGameOver(true);
            connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage("Checkmate. " + username + " wins!")), null);
        } else if (game.isInStalemate(game.getTeamTurn())) {
            game.setGameOver(true);
            connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage("Stalemate: game is a draw")), null);
        } else if (game.isInCheck(game.getTeamTurn())) {
            connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage("Check.")), null);
        }

        try {
            dataAccess.updateGame(gameData);
        } catch (DataAccessException e) {
            connectionManager.sendError(session, "Server Error");
            return;
        }

        connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage(gameData.game())), null);
        connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage(username + " makes move: " + move)), session);
    }

    private void leave(Session session, String username, GameData gameData) throws IOException {
        connectionManager.remove(gameData.gameID(), session);

        String notificationJson = gson.toJson(new ServerMessage(username + " has left"));
        connectionManager.broadcast(gameData.gameID(), notificationJson, session);
    }

    private void resign(Session session, String username, GameData gameData) throws IOException {
        ChessGame game = gameData.game();
        game.setGameOver(true);
        String otherUser;
        if (username.equals(gameData.whiteUsername())) {
            otherUser = gameData.blackUsername();
        } else {
            otherUser = gameData.whiteUsername();
        }
        connectionManager.broadcast(gameData.gameID(), gson.toJson(new ServerMessage(username + " resigns, " + otherUser + " wins!")), null);
    }

}