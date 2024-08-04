package ui;

import chess.ChessBoard;
import models.UserData;

public class LoginUI implements UI {
    private final ChessClient chessClient;

    public LoginUI(ChessClient chessClient) {
        this.chessClient = chessClient;
    }

    @Override
    public String eval(String cmd, String[] args) {
        return switch (cmd) {
            case "login" -> login(args);
            case "register" -> register(args);
            case "quit" -> "quit";
            default -> help();
        };
    }

    @Override
    public String help() {
        return """
        Login:    "login" <username> <password>
        Register: "register" <username> <password> <email>
        Quit:     "quit"
        Help:     "help"
        """;
    }

    private String login(String[] args) {
        if (args.length != 2) {
            return "login <username> <password>";
        }
        UserData req = new UserData(args[0], args[1], null);

        chessClient.setAsLoggedIn();
        return null;
    }

    private String register(String[] args) {
        if (args.length != 3) {
            return "register <username> <password> <email>";
        }
        UserData req = new UserData(args[0], args[1], args[2]);

        chessClient.setAsLoggedIn();
        return null;
    }
}
