package ui;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {

    private String serverUrl;
    private ServerFacade server;
    UI ui;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        UI ui = new LoginUI(this, server);
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
}
