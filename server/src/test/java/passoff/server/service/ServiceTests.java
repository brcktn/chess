package passoff.server.service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.*;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.DataService;
import service.UserService;

public class ServiceTests {
    private static DataAccess dataAccess;
    private static UserData newUserData;
    private static GameData newGameData;
    static String username;
    static String password;
    static String email;

    @BeforeAll
    public static void setup() {
        dataAccess = new MemoryDAO();
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
    @Order(1)
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
    @Order(2)
    public void testSuccessfulRegisterUser() throws DataAccessException, BadRequestException, AlreadyTakenException {
        new UserService(dataAccess).register(newUserData);

        Assertions.assertEquals(dataAccess.getUser(username), newUserData);
    }

    @Test
    @Order(3)
    public void testRegisterEmptyUsername() {
        newUserData = new UserData("", password, email);

        Assertions.assertThrows(BadRequestException.class, () ->
                new UserService(dataAccess).register(newUserData));
    }

    @Test
    @Order(4)
    public void testRegisterEmptyPassword() {
        newUserData = new UserData(username, "", email);

        Assertions.assertThrows(BadRequestException.class, () ->
            new UserService(dataAccess).register(newUserData));
    }

    @Test
    @Order(5)
    public void testRegisterEmptyEmail() {
        newUserData = new UserData(username, password, "");

        Assertions.assertThrows(BadRequestException.class, () ->
            new UserService(dataAccess).register(newUserData));
    }

    @Test
    @Order(6)
    public void testRegisterUsernameTaken() {
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            new UserService(dataAccess).register(newUserData);
            new UserService(dataAccess).register(newUserData);
        });
    }


}
