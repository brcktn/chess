package service;

import dataaccess.*;
import models.AuthData;
import models.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) throws BadRequestException, DataAccessException, AlreadyTakenException {
        if (user.password() == null || user.password().isEmpty() ||
            user.username() == null || user.username().isEmpty() ||
            user.email() == null || user.email().isEmpty()) {
            throw new BadRequestException("");
        }
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyTakenException("");
        }

        dataAccess.createUser(user);
        return dataAccess.createAuth(user.username());
    }

    public AuthData login(UserData user) throws UnauthorizedException, DataAccessException {
        if (dataAccess.getUser(user.username()) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        if (!dataAccess.checkPassword(user)) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        return dataAccess.createAuth(user.username());
    }

    public void logout(String authToken) throws UnauthorizedException, DataAccessException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        dataAccess.deleteAuth(authToken);
    }
}
