package ui;

import models.JoinGameRequest;
import server.ResponseException;
import server.ServerFacade;

import java.io.IOException;

public class ObserveUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;
    private final int gameID;

    public ObserveUI(ChessClient chessClient, ServerFacade serverFacade, int gameID) {
        this.chessClient = chessClient;
        this.serverFacade = serverFacade;
        this.gameID = gameID;
    }

    @Override
    public String eval(String cmd, String[] args) {
        try {
            return switch (cmd) {
                case "leave" -> leaveGame();
                case "highlight" -> highlightMoves(args);
                case "redraw" -> redraw();
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
        Leave game:        "leave"
        Highlight moves:   "highlight <b1>"
        Redraw board:      "redraw"
        Help:              "help"
        Quit:              "quit"
        """;
    }

    private String leaveGame() {
        chessClient.setAsOutOfGame();
        System.out.print(chessClient.getUi().help());
        return "";
    }

    private String highlightMoves(String[] args) {
        return null;
    }

    private String redraw() {
        return null;
    }
}
