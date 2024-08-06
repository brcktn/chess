package ui;

import models.UserData;
import server.ResponseException;
import server.ServerFacade;

public class LoginUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;

    public LoginUI(ChessClient chessClient, ServerFacade server) {
        this.chessClient = chessClient;
        this.serverFacade = server;
    }

    @Override
    public String eval(String cmd, String[] args) {
        try {
            return switch (cmd) {
                case "login" -> login(args);
                case "register" -> register(args);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Throwable e) {
            return e.getMessage();
        }
    }

    @Override
    public String help() {
        return """
        Login:    "login <username> <password>"
        Register: "register" <username> <password> <email>"
        Quit:     "quit"
        Help:     "help"
        """;
    }

    private String login(String[] args) throws ResponseException {
        if (args.length != 2) {
            return "login <username> <password>";
        }
        UserData req = new UserData(args[0], args[1], null);
        chessClient.setAsLoggedIn(serverFacade.login(req).authToken());
        return "Logged in!";
    }

    private String register(String[] args) throws ResponseException {
        if (args.length != 3) {
            return "register <username> <password> <email>";
        }
        UserData req = new UserData(args[0], args[1], args[2]);
        var a = req;
        chessClient.setAsLoggedIn(serverFacade.register(req).authToken());
        return "Registered!";
    }
}
