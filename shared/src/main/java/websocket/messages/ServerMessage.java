package websocket.messages;

import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;
    String message;
    ChessGame game;
    ChessGame.TeamColor teamColor;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type, String message) {
        this.serverMessageType = type;
        this.message = message;
    }

    public ServerMessage(ChessGame gameData, ChessGame.TeamColor teamColor) {
        this.game = gameData;
        this.teamColor = teamColor;
        this.serverMessageType = ServerMessageType.LOAD_GAME;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    public String getMessage() {
        return this.message;
    }

    public ChessGame getGame() {
        return this.game;
    }

    public ChessGame.TeamColor getTeamColor() {
        return this.teamColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage)) {
            return false;
        }
        ServerMessage that = (ServerMessage) o;
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}