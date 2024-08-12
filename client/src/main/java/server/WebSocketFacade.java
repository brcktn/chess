package server;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import ui.ChessClient;
import ui.ChessRender;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import javax.websocket.*;

public class WebSocketFacade extends Endpoint {
    Session session;
    ChessClient chessClient;

    public WebSocketFacade(String url, ChessClient chessClient) throws ResponseException {
        this.chessClient = chessClient;
        try {
            url = url.replace("http", "ws");
            URI uri = new URI(url + "/ws");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                        ChessBoard board = serverMessage.getGame().getBoard();
                        System.out.print(ChessRender.render(board, chessClient.getViewColor()));
                    } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                        System.out.print("Error: " + serverMessage.getMessage() + "\n");
                    } else {
                        System.out.print(serverMessage.getMessage() + "\n");
                    }
                }
            });


        } catch (URISyntaxException | IOException | DeploymentException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void sendString(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private void sendMessage(UserGameCommand cmd)  throws IOException {
        session.getBasicRemote().sendText(new Gson().toJson(cmd));
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(String authToken, int gameId, ChessGame.TeamColor teamColor) throws IOException {
        sendMessage(new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameId));
    }

    public void makeMove(String authToken, int gameId, ChessMove move) throws IOException {
        sendMessage(new UserGameCommand(authToken, gameId, move));
    }

    public void leaveGame(String authToken, int gameId) throws IOException {
        sendMessage(new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameId));
    }

}