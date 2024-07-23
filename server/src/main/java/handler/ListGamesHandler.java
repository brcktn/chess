package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import service.UnauthorizedException;
import service.GameService;
import spark.Request;

public class ListGamesHandler {
    DataAccess dataAccess;
    public ListGamesHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public String listGames(Request req) throws UnauthorizedException, DataAccessException {
        String authToken  = req.headers("Authorization");
        Gson gson = new Gson();
        return gson.toJson(new GameService(dataAccess).listGames(authToken));
    }
}
