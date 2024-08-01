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

    /**
     * @return GameData for game with given ID, null if ID does not exist
     */
    GameData getGame(int gameID) throws DataAccessException;

    /**
     * @return ListGamesResponse object including all games in database
     */
    ListGamesResponse listGames() throws DataAccessException;

    /**
     * Replaces data for given game in database with gameData
     * @throws DataAccessException if gameID for gameData doesn't exist
     */
    void updateGame(GameData gameData) throws DataAccessException;

    /**
     * @return new authData for given username, null if user doesn't exist
     */
    AuthData createAuth(String username) throws DataAccessException;

    /**
     *
     * @return authData including username associated with given authToken, null if authToken doesn't exist
     */
    AuthData getAuth(String authToken) throws DataAccessException;

    /**
     * removes authData associated with given authToken
     * @throws DataAccessException if authToken doesn't exist
     */
    void deleteAuth(String authToken) throws DataAccessException;

    /**
     * @param userData userData for given user
     * @return True if password matches hashed password in database
     */
    boolean checkPassword(UserData userData) throws DataAccessException;
}
