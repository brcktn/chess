package ui;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import models.JoinGameRequest;
import server.ResponseException;
import server.ServerFacade;
import server.WebSocketFacade;

import java.io.IOException;

public class GameUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;
    private final WebSocketFacade webSocketFacade;
    private final int gameID;

    public GameUI(ChessClient chessClient, ServerFacade server, WebSocketFacade webSocket, int gameID) {
        this.chessClient = chessClient;
        this.serverFacade = server;
        this.webSocketFacade = webSocket;
        this.gameID = gameID;
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
        Make move:         "move <b1> <c1> (<q>)"
        Leave game:        "leave"
        Resign from game:  "resign"
        Highlight moves:   "highlight <b1>"
        Redraw board:      "redraw"
        Help:              "help"
        """;
    }

    private String makeMove(String[] args) {
        if (args.length < 2 || args.length > 3) {
            return "move <starting square> <destination square> (<promotion piece>";
        }
        ChessPiece.PieceType promotionPiece = null;
        if (args.length == 3) {

            promotionPiece = switch (args[2]) {
                case "q", "queen" -> ChessPiece.PieceType.QUEEN;
                case "r", "rook" -> ChessPiece.PieceType.ROOK;
                case "b", "bishop" -> ChessPiece.PieceType.BISHOP;
                case "k", "n", "knight" -> ChessPiece.PieceType.KNIGHT;
                default -> null;
            };
            if (promotionPiece == null) {
                return "%s is not a valid piece".formatted(args[2]);
            }
        }

        ChessPosition startPos = stringToPosition(args[0]);
        if (startPos == null) {
            return "%s is invalid".formatted(args[0]);
        }
        ChessPosition endPos = stringToPosition(args[1]);
        if (endPos == null) {
            return "%s is invalid".formatted(args[1]);
        }

        ChessMove move = new ChessMove(startPos, endPos, promotionPiece);
        try {
            webSocketFacade.makeMove(chessClient.getAuthToken(), gameID, move);
        } catch (IOException e) {
            return "Could not make move: " + e.getMessage();
        }
        return "";


    }

    private String leaveGame() {
        try {
            webSocketFacade.leaveGame(chessClient.getAuthToken(), gameID);
            chessClient.getServer().leaveGame(new JoinGameRequest(chessClient.getViewColor(), gameID));
            chessClient.setAsOutOfGame();
        } catch (IOException | ResponseException e) {
            return "Could not leave game: " + e.getMessage();
        }
        System.out.print(chessClient.getUi().help());
        return "";
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

    private ChessPosition stringToPosition(String posString) {
        if (posString.length() != 2) {
            return null;
        }
        char colChar = posString.charAt(0);
        char rowChar = posString.charAt(1);

        int col = colChar - 'a' + 1;
        int row = Character.getNumericValue(rowChar);

        if (row <= 0 || row > 8 || col <= 0 || col > 8) {
            return null;
        }
        return new ChessPosition(row, col);
    }
}
