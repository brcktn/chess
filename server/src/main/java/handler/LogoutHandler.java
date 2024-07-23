package handler;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;

public class LogoutHandler {
    private final DataAccess dataAccess;

    public LogoutHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void logout(Request req) throws UnauthorizedException, DataAccessException {
        String authToken = req.headers("Authorization");
        new UserService(dataAccess).logout(authToken);
    }
}
