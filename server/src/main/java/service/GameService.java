package service;

import chess.ChessGame;
import dataaccess.*;
import models.GameData;
import models.JoinGameRequest;
import models.ListGamesResponse;

public class GameService {
    DataAccess dataAccess;
    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public GameData createGame(GameData gameData, String authToken) throws UnauthorizedException, DataAccessException, BadRequestException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        if (gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequestException("Error: bad request");
        }

        GameData newGame = new GameData(0, null, null,
                                        gameData.gameName(), new ChessGame());
        return dataAccess.createGame(newGame);
    }

    public void joinGame(JoinGameRequest request, String authToken) throws UnauthorizedException, DataAccessException, BadRequestException, AlreadyTakenException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        if (dataAccess.getGame(request.gameID()) == null) {
            throw new BadRequestException("Error: bad request");
        }

        GameData game = dataAccess.getGame(request.gameID());
        ChessGame.TeamColor requestedTeam = request.playerColor();
        String username = dataAccess.getAuth(authToken).username();

        if (requestedTeam == null) {
            throw new BadRequestException("Error: bad request");
        }

        if (requestedTeam == ChessGame.TeamColor.WHITE) {
            if (game.whiteUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }

            dataAccess.updateGame(new GameData(game.gameID(), username, game.blackUsername(),
                                  game.gameName(), game.game()));

        } if (requestedTeam == ChessGame.TeamColor.BLACK) {
            if (game.blackUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }

            dataAccess.updateGame(new GameData(game.gameID(), game.whiteUsername(), username,
                                  game.gameName(), game.game()));
        }
    }

    public ListGamesResponse listGames(String authToken) throws DataAccessException, UnauthorizedException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        return dataAccess.listGames();
    }
}
