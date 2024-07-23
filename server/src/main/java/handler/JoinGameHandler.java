package handler;

import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import models.JoinGameRequest;
import service.GameService;
import spark.Request;

public class JoinGameHandler {
    private final DataAccess dataAccess;
    public JoinGameHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void joinGame(Request req) throws UnauthorizedException, BadRequestException, DataAccessException {
        String authToken = req.headers("Authorization");
        Gson gson = new Gson();
        JoinGameRequest joinGameRequest = gson.fromJson(req.body(), JoinGameRequest.class);
        new GameService(dataAccess).joinGame(joinGameRequest, authToken);
    }
}
