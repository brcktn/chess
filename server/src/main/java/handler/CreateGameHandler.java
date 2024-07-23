package handler;

import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import models.GameData;
import service.GameService;
import spark.Request;

public class CreateGameHandler {
    private final DataAccess dataAccess;
    public CreateGameHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public String createGame(Request req) throws UnauthorizedException, BadRequestException, DataAccessException {
        String authToken = req.headers("Authorization");
        Gson gson = new Gson();
        GameData gameData = gson.fromJson(req.body(), GameData.class);
        return gson.toJson(new GameService(dataAccess).createGame(gameData, authToken));
    }
}
