package ui;

import server.ServerFacade;
import server.WebSocketFacade;

public class GameUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;
    private final WebSocketFacade webSocketFacade;

    public GameUI(ChessClient chessClient, ServerFacade server, WebSocketFacade webSocket) {
        this.chessClient = chessClient;
        this.serverFacade = server;
        this.webSocketFacade = webSocket;
    }

    @Override
    public String eval(String cmd, String[] args) {
        try {
            return switch (cmd) {
                case "move" -> makeMove(args);
                case "leave" -> leaveGame();
                case "resign" -> resign();
                case "highlight" -> highlightMoves(args);
                case "redraw" -> redraw();
                default -> help();
            };
        } catch (Throwable e) {
            return e.getMessage();
        }
    }

    @Override
    public String help() {
        return """
        Make move:         "move <b1> <c1>"
        Leave game:        "leave"
        Resign from game:  "resign"
        Highlight moves:   "highlight <b1>"
        Redraw board:      "redraw"
        Help:              "help"
        """;
    }

    private String makeMove(String[] args) {
        return null;
    }

    private String leaveGame() {
        return null;
    }

    private String resign() {
        return null;
    }

    private String highlightMoves(String[] args) {
        return null;
    }

    private String redraw() {
        return null;
    }
}
