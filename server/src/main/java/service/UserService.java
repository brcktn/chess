package service;

import dataaccess.AlreadyTakenException;
import dataaccess.BadRequestException;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import models.AuthData;
import models.UserData;

import java.util.Objects;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) throws BadRequestException, AlreadyTakenException, DataAccessException {
        if (user.password() == null || user.password().isEmpty() ||
                user.username() == null || user.username().isEmpty()) {
            throw new BadRequestException("");
        }
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyTakenException("");
        }

        dataAccess.createUser(user);
        return dataAccess.createAuth(user.username());
    }

    public AuthData login(UserData user) throws DataAccessException {
        if (!Objects.equals(user.password(), dataAccess.getUser(user.username()).password())) {
            throw new DataAccessException("Error: unauthorized");
        }

        return dataAccess.createAuth(user.username());
    }

    public void logout(String authToken) throws DataAccessException {
        dataAccess.deleteAuth(authToken);
    }
}
