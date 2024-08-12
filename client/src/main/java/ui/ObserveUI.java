package ui;

import server.ServerFacade;

public class ObserveUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;

    public ObserveUI(ChessClient chessClient, ServerFacade serverFacade) {
        this.chessClient = chessClient;
        this.serverFacade = serverFacade;
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
        return null;
    }

    private String highlightMoves(String[] args) {
        return null;
    }

    private String redraw() {
        return null;
    }
}
