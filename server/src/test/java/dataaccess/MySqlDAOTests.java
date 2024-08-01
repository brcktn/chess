package dataaccess;

import chess.ChessGame;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import service.BadRequestException;
import service.DataService;

public class MySqlDAOTests {
    private static DataAccess dataAccess;
    private static UserData newUserData;
    private static GameData newGameData;
    static String username;
    static String password;
    static String email;

    @BeforeAll
    public static void setup() throws DataAccessException {
        dataAccess = new MySqlDAO();
        username = "user1001";
        password = "drowssap";
        email = "user@gmail.com";
    }

    @BeforeEach
    public void beforeEach() {
        newUserData = new UserData(username, password, email);
        newGameData = new GameData(0, null, null, "test game", new ChessGame());
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        dataAccess.clear();
    }

    @Test
    void testClear() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String authToken = dataAccess.createAuth(username).authToken();
        int gameID = dataAccess.createGame(newGameData).gameID();

        new DataService(dataAccess).clear();

        Assertions.assertNull(dataAccess.getUser(username));
        Assertions.assertNull(dataAccess.getAuth(authToken));
        Assertions.assertNull(dataAccess.getGame(gameID));
    }

    @Test
    void testCreateUserSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        UserData user = dataAccess.getUser(username);

        Assertions.assertEquals(user.email(), newUserData.email());
    }

    @Test
    void testCreateUserNoUsername() throws DataAccessException {
        UserData badUserData = new UserData(null, "pass", email);
        Assertions.assertThrows(DataAccessException.class, () ->
                dataAccess.createUser(badUserData));
    }

    @Test
    void testGetUserSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        UserData user = dataAccess.getUser(username);

        Assertions.assertEquals(user.email(), newUserData.email());
    }

    @Test
    void testGetUserNotFound() throws DataAccessException {
        UserData user = dataAccess.getUser(username);
        Assertions.assertNull(user);
    }

    @Test
    void testCreateGameSuccess() throws DataAccessException {
        int gameID = dataAccess.createGame(newGameData).gameID();
        GameData game = dataAccess.getGame(gameID);
        Assertions.assertEquals(game.game(), newGameData.game());
    }

    @Test
    void testCreateGameNoGame() {
        GameData badGame = new GameData(0, null, null, "test game", null);
        Assertions.assertThrows(DataAccessException.class, () ->
                dataAccess.createGame(badGame));
    }

    @Test
    void testGetGameSuccess() throws DataAccessException {
        GameData addedGame = dataAccess.createGame(newGameData);
        GameData gotGame = dataAccess.getGame(addedGame.gameID());
        Assertions.assertEquals(addedGame, gotGame);
    }

    @Test
    void testGetGameNotFound() throws DataAccessException {
        Assertions.assertNull(dataAccess.getGame(2));
    }

    @Test
    void testListGamesSuccess() throws DataAccessException {
        dataAccess.createGame(newGameData);
        dataAccess.createGame(newGameData);
        dataAccess.createGame(newGameData);
        Assertions.assertEquals(3, dataAccess.listGames().games().size());
    }

    @Test
    void testListGamesEmpty() throws DataAccessException {
        Assertions.assertEquals(0, dataAccess.listGames().games().size());
    }

    @Test
    void testUpdateGameSuccess() throws DataAccessException {
        GameData gameData = dataAccess.createGame(newGameData);
        dataAccess.updateGame(new GameData(gameData.gameID(), "whiteUser",
                null, gameData.gameName(), gameData.game()));
        Assertions.assertEquals("whiteUser", dataAccess.getGame(gameData.gameID()).whiteUsername());
    }

    @Test
    void testUpdateGameNotFound() {
        Assertions.assertThrows(DataAccessException.class, () ->
                dataAccess.updateGame(newGameData));
    }

    @Test
    void testCreateAuthSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String authToken = dataAccess.createAuth(username).authToken();
        Assertions.assertEquals(username, dataAccess.getAuth(authToken).username());
    }

    @Test
    void testCreateAuthNoUsername() throws DataAccessException {
        Assertions.assertNull(dataAccess.getAuth("badUser"));
    }

    @Test
    void testGetAuthSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String authToken = dataAccess.createAuth(username).authToken();
        Assertions.assertEquals(username, dataAccess.getAuth(authToken).username());
    }

    @Test
    void testGetAuthNotFound() throws DataAccessException {
        Assertions.assertNull(dataAccess.getAuth("badAuth"));
    }

    @Test
    void testDeleteAuthSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String authToken = dataAccess.createAuth(username).authToken();
        dataAccess.deleteAuth(authToken);
        Assertions.assertNull(dataAccess.getAuth(authToken));
    }

    @Test
    void testDeleteAuthNotFound() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () ->
                dataAccess.deleteAuth("badAuth"));
    }

    @Test
    void testCheckPasswordSuccess() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String hash = dataAccess.getUser(username).password();
        Assertions.assertTrue(BCrypt.checkpw(password, hash));
    }

    @Test
    void testCheckPasswordIncorrect() throws DataAccessException {
        dataAccess.createUser(newUserData);
        String hash = dataAccess.getUser(username).password();
        Assertions.assertFalse(BCrypt.checkpw("badPassword", hash));
    }
}
