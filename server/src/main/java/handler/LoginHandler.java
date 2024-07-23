package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import service.UnauthorizedException;
import models.UserData;
import service.UserService;
import spark.Request;

public class LoginHandler {
    private final DataAccess dataAccess;
    public LoginHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public String login(Request req) throws DataAccessException, UnauthorizedException {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(req.body(), UserData.class);
        return gson.toJson(new UserService(dataAccess).login(userData));
    }

}
