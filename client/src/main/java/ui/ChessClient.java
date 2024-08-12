package ui;

import chess.ChessGame;
import server.ResponseException;
import server.ServerFacade;
import server.WebSocketFacade;

import java.util.Arrays;

public class ChessClient {

    private final ServerFacade server;
    private WebSocketFacade webSocketFacade;
    private String authToken;
    UI ui;
    String serverUrl;
    ChessGame.TeamColor viewColor;

    public ChessClient(String serverUrl) {
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl, this);
        this.ui = new LoginUI(this, server);
    }

    public String eval(String input) {

        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return ui.eval(cmd, params);
    }

    public void setAsLoggedIn(String authToken) {
        ui = new MainUI(this);
        this.authToken = authToken;
    }

    public void setAsLoggedOut() {
        ui = new LoginUI(this, server);
        this.authToken = null;
    }

    public void setAsInGame(int gameID, ChessGame.TeamColor playerColor) throws ResponseException {
        this.viewColor = playerColor;
        setWebSocketFacade(new WebSocketFacade(getServerUrl(), this));
        ui = new GameUI(this, server, webSocketFacade, gameID);
    }

    public void setAsObserve() throws ResponseException {
        this.viewColor = ChessGame.TeamColor.WHITE;
        setWebSocketFacade(new WebSocketFacade(getServerUrl(), this));
        ui = new ObserveUI(this, server);
    }

    public void setAsOutOfGame() {
        setWebSocketFacade(null);
        ui = new MainUI(this);
        this.viewColor = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ServerFacade getServer() {
        return server;
    }

    public ChessGame.TeamColor getViewColor() {
        return viewColor;
    }

    public WebSocketFacade getWebSocketFacade() {
        return webSocketFacade;
    }

    public void setWebSocketFacade(WebSocketFacade webSocketFacade) {
        this.webSocketFacade = webSocketFacade;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public UI getUi() {
        return ui;
    }
}