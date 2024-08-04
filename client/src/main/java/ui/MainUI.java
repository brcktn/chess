package ui;

import server.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class MainUI implements UI {
    private ChessClient chessClient;
    private ServerFacade server;

    public MainUI(ChessClient chessClient, ServerFacade server) {
        this.chessClient = chessClient;
        this.server = server;
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
                default -> help();
            };
        } catch (Throwable e) {
            return e.getMessage();
        }
    }

    @Override
    public String help() {
        return "";
    }

    private String logout() throws ResponseException {
        server.logout();
        chessClient.setAsLoggedOut();
        return "Logged out!";
    }

    private String createGame(String[] args) {
        return null;
    }

    private String listGames() {
        return null;
    }

    private String playGame(String[] args) {
        return null;
    }

    private String observeGame(String[] args) {
        return null;
    }
}
