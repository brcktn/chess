package dataaccess;

import models.AuthData;
import models.GameData;
import models.ListGamesResponse;
import models.UserData;

public interface DataAccess {
    /**
     * Clears the database
     */
    void clear() throws DataAccessException;

    /**
     * Adds a user with given username password and email
     * @throws DataAccessException if user already exists
     */
    void createUser(UserData userData) throws DataAccessException;

    /**
     * @return userData for user with given username, null if user does not exist
     */
    UserData getUser(String username) throws DataAccessException;

    /**
     * adds black and white usernames, game name, and ChessGame to the database
     * automatically generates new unique gameID
     * @param gameData includes usernames, game name, and ChessGame
     * @return GameData with new generated gameID
     * @throws DataAccessException if ChessGame is null
     */
    GameData createGame(GameData gameData) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    ListGamesResponse listGames() throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    boolean checkPassword(UserData user) throws DataAccessException;
}
