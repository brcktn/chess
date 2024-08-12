package ui;

import server.ResponseException;
import server.ServerFacade;
import server.WebSocketFacade;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ChessClient {

    private final ServerFacade server;
    private WebSocketFacade webSocketFacade;
    private String authToken;
    UI ui;
    String serverUrl;

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
        ui = new MainUI(this, server, webSocketFacade);
        this.authToken = authToken;
    }

    public void setAsLoggedOut() {
        ui = new LoginUI(this, server);
        this.authToken = null;
    }

    public void setAsInGame() throws ResponseException {
        ui = new GameUI(this, server, webSocketFacade);
        setWebSocketFacade(new WebSocketFacade(getServerUrl()));
    }

    public void setAsObserve() throws ResponseException {
        ui = new ObserveUI(this, server);
        setWebSocketFacade(new WebSocketFacade(getServerUrl()));
    }

    public void setAsOutOfGame() {
        ui = new MainUI(this, server, webSocketFacade);
        setWebSocketFacade(null);
    }

    public String getAuthToken() {
        return authToken;
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
}