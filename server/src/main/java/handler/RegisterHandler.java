package handler;

import com.google.gson.Gson;
import service.AlreadyTakenException;
import service.BadRequestException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import models.UserData;
import service.UserService;
import spark.Request;

public class RegisterHandler {
    private final DataAccess dataAccess;

    public RegisterHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public String register(Request req) throws DataAccessException, BadRequestException, AlreadyTakenException {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(req.body(), UserData.class);
        return gson.toJson(new UserService(dataAccess).register(userData));
    }


}
