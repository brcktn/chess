package handler;

import com.google.gson.Gson;
import dataaccess.*;
import models.JoinGameRequest;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.GameService;
import service.UnauthorizedException;
import spark.Request;

public class LeaveGameHandler {
    private final DataAccess dataAccess;
    public LeaveGameHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void leaveGame(Request req) throws UnauthorizedException, BadRequestException, DataAccessException, AlreadyTakenException {
        String authToken = req.headers("Authorization");
        Gson gson = new Gson();
        JoinGameRequest joinGameRequest = gson.fromJson(req.body(), JoinGameRequest.class);
        new GameService(dataAccess).leaveGame(joinGameRequest, authToken);
    }
}
