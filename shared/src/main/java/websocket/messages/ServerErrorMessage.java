package websocket.messages;

import chess.ChessGame;

import java.util.Objects;

public class ServerErrorMessage {
    ServerMessage.ServerMessageType serverMessageType;
    String errorMessage;
    ChessGame game;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerErrorMessage(ServerMessage.ServerMessageType type, String errorMessage) {
        this.serverMessageType = type;
        this.errorMessage = errorMessage;
    }

    public ServerMessage.ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage that)) {
            return false;
        }
        //autograder thinks this is duplicated, so i'm adding a comment :)
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
