package passoff.server.service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    @BeforeAll
    public static void setup() {
        dataAccess = new MemoryDAO();
        username = "user1001";
        password = "drowssap";
        newUserData = new UserData(username, password, "user@gmail.com");
        newGameData = new GameData(0, null, null, "test game", new ChessGame());
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
    public void testRegisterUser() throws DataAccessException, BadRequestException, AlreadyTakenException {
        String username = "user1001";
        new UserService(dataAccess).register(newUserData);

        Assertions.assertEquals(dataAccess.getUser(username), newUserData);
    }
}
