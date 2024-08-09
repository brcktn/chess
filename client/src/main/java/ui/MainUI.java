package ui;

import chess.ChessBoard;
import chess.ChessGame;
import models.GameData;
import models.JoinGameRequest;
import models.ListGamesResponse;
import server.ResponseException;
import server.ServerFacade;

import java.util.HashMap;

import static ui.EscapeSequences.ERASE_SCREEN;

public class MainUI implements UI {
    private final ChessClient chessClient;
    private final ServerFacade serverFacade;
    private HashMap<Integer, GameData> gameDataMap;

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
               Play game:    "play <gameNum> <teamcolor>"
               Observe game: "observe <gameNum>"
               Quit:         "quit"
               Help:         "help"
               """;
    }

    private String logout() throws ResponseException {
        serverFacade.logout();
        chessClient.setAsLoggedOut();
        gameDataMap.clear();
        return "Logged out!";
    }

    private String createGame(String[] args) throws ResponseException {
        if (args.length != 1) {
            return "create <game_name>";
        }
        StringBuilder builder = new StringBuilder();
        GameData gameData = serverFacade.createGame(new GameData(0,null, null, args[0], new ChessGame()));
        String gameName = gameData.gameName();
        builder.append("New game \"");
        builder.append(gameName);
        builder.append("\" created!");
        return builder.toString();
    }

    private String listGames() throws ResponseException {
        StringBuilder builder = new StringBuilder();
        ListGamesResponse response = serverFacade.listGames();
        gameDataMap = new HashMap<>();
        for (int i = 0; i < response.games().size(); i++) {
            gameDataMap.put(i, response.games().get(i));
        }

        int j = 0;
        for (GameData gameData : gameDataMap.values()) {
            String whitePlayer = gameData.whiteUsername();
            String blackPlayer = gameData.blackUsername();
            builder.append("Game number: ");
            builder.append(j);
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
            j++;
        }

        return builder.toString();
    }

    private String playGame(String[] args) throws ResponseException {
        if (args.length != 2) {
            return """
                    play <gameNum> <color>
                    """;
        }
        int gameNumber;
        try {
            gameNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return """
                    invalid game number
                    play <gameNum> <color>
                    """;
        }
        int gameID;
        try {
            gameID = gameDataMap.get(gameNumber).gameID();
        } catch (NullPointerException e) {
            return """
                   invalid game number
                   play <gameID> <color>
                   """;
        }
        if (args[1].equalsIgnoreCase("white")) {
            serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID));
            return "Joined game as white!\n" +
                    ChessRender.render(new ChessGame().getBoard());
        } else if (args[1].equalsIgnoreCase("black")) {
            serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, gameID));
            return "Joined game as black!\n" +
                    ChessRender.render(new ChessGame().getBoard(), ChessGame.TeamColor.BLACK);
        }
        return """
                invalid team color
                play <gameNum> <color>
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
