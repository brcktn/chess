package ui;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {


    private String serverUrl;
    private ServerFacade server;
    boolean loggedIn = false;
    UI ui;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        UI ui = new LoginUI();
    }

    public String eval(String input) {
        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return ui.eval(cmd, params);
    }
}
