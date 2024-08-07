package ui;

import chess.ChessBoard;
import chess.ChessGame;
import models.GameData;
import models.JoinGameRequest;
import models.ListGamesResponse;
import server.ResponseException;
import server.ServerFacade;

import static ui.EscapeSequences.ERASE_SCREEN;

public class MainUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;

    public MainUI(ChessClient chessClient, ServerFacade server) {
        this.chessClient = chessClient;
        this.serverFacade = server;
    }

    @Override
    public String eval(String cmd, String[] args) {
        try {
            return switch (cmd) {
                case "logout" -> logout();
                case "create" -> createGame(args);
                case "list" -> listGames();
                case "play" -> playGame(args);
                case "observe" -> observeGame(args);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Throwable e) {
            return e.getMessage();
        }
    }

    @Override
    public String help() {
        return ERASE_SCREEN + """
               Logout:       "logout"
               Create game:  "create <gamename>"
               List games:   "list"
               Play game:    "play <gameID> <teamcolor>"
               Observe game: "observe <gameID>"
               Quit:         "quit"
               Help:         "help"
               """;
    }

    private String logout() throws ResponseException {
        serverFacade.logout();
        chessClient.setAsLoggedOut();
        return "Logged out!";
    }

    private String createGame(String[] args) throws ResponseException {
        if (args.length != 1) {
            return "create <game_name>";
        }
        StringBuilder builder = new StringBuilder();
        GameData gameData = serverFacade.createGame(new GameData(0,null, null, args[0], new ChessGame()));
        int gameID = gameData.gameID();
        String gameName = gameData.gameName();
        builder.append("New game \"");
        builder.append(gameName);
        builder.append("\" created with ID ");
        builder.append(gameID);
        return builder.toString();
    }

    private String listGames() throws ResponseException {
        StringBuilder builder = new StringBuilder();
        ListGamesResponse response = serverFacade.listGames();
        for (GameData gameData : response.games()) {
            String whitePlayer = gameData.whiteUsername();
            String blackPlayer = gameData.blackUsername();
            builder.append("Game ID: ");
            builder.append(gameData.gameID());
            builder.append(",   Game name: ");
            builder.append(gameData.gameName());
            if (whitePlayer != null) {
                builder.append(", white: ");
                builder.append(whitePlayer);
            }
            if (blackPlayer != null) {
                builder.append(", black: ");
                builder.append(blackPlayer);
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private String playGame(String[] args) throws ResponseException {
        if (args.length != 2) {
            return """
                    play <gameID> <color>
                    """;
        }
        int gameId;
        try {
            gameId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return """
                    invalid gameID
                    play <gameID> <color>
                    """;
        }
        if (args[1].equalsIgnoreCase("white")) {
            serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, gameId));
            return "Joined game as white!\n" +
                    ChessRender.render(new ChessGame().getBoard());
        } else if (args[1].equalsIgnoreCase("black")) {
            serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, gameId));
            return "Joined game as black!\n" +
                    ChessRender.render(new ChessGame().getBoard(), ChessGame.TeamColor.BLACK);
        }
        return """
                invalid team color
                play <gameID> <color>
                """;
    }

    private String observeGame(String[] args) {
        ChessBoard newBoard = new ChessGame().getBoard();

        return "White view: \n" +
                ChessRender.render(newBoard) +
                "\n\nBlack view: \n" +
                ChessRender.render(newBoard, ChessGame.TeamColor.BLACK);
    }
}
