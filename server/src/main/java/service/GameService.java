package service;

import chess.ChessGame;
import dataaccess.BadRequestException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import models.GameData;

public class GameService {
    DataAccess dataAccess;
    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public GameData createGame(GameData gameData, String authToken) throws UnauthorizedException, DataAccessException, BadRequestException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        dataAccess.createGame(gameData);

        if (gameData.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }

        GameData newGame = new GameData(0, null, null,
                                        gameData.gameName(), new ChessGame());
        return dataAccess.createGame(newGame);
    }
}
