package dataaccess;

import models.AuthData;
import models.GameData;
import models.UserData;

import java.util.Collection;

public interface DataAccess {
    void clear() throws DataAccessException;

    void createUser(UserData u) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    GameData createGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    void createAuth(AuthData auth) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;
}
