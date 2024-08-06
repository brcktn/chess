package ui;

import chess.ChessGame;
import models.GameData;
import models.JoinGameRequest;
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
        return serverFacade.createGame(new GameData(0,null, null, args[0], new ChessGame())).toString();
    }

    private String listGames() throws ResponseException {
        return serverFacade.listGames().toString();
    }

    private String playGame(String[] args) throws ResponseException {
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
            return new ChessGame().toString();
        } else if (args[1].equalsIgnoreCase("black")) {
            serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, gameId));
            return new ChessGame().getBoard().toString();
        }
        return """
                invalid team color
                play <gameID> <color>
                """;
    }

    private String observeGame(String[] args) {
        return new ChessGame().getBoard().toString();
    }
}
