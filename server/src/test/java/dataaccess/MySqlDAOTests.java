package dataaccess;

import chess.ChessGame;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.*;
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

        Assertions.assertEquals(newUserData.email(), user.email());
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

        Assertions.assertEquals(newUserData.email(), user.email());
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
        Assertions.assertEquals(newGameData.game().getBoard(), game.game().getBoard());
    }

    @Test
    void testCreateGameNoGame() throws DataAccessException {
        GameData badGame = new GameData(0, null, null, "test game", null);
        Assertions.assertThrows(DataAccessException.class, () ->
                dataAccess.createGame(badGame));
    }
}
