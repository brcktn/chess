package ui;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {

    private final String serverUrl;
    private final ServerFacade server;
    private String authToken;
    UI ui;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.ui = new LoginUI(this, server);
    }

    public String eval(String input) {

        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return ui.eval(cmd, params);
    }

    public void setAsLoggedIn() {
        ui = new MainUI(this, server);
    }

    public void setAsLoggedOut() {
        ui = new LoginUI(this, server);
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
