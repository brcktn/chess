package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import dataaccess.MySqlDAO;
import models.AuthData;
import models.GameData;
import models.JoinGameRequest;
import models.UserData;
import org.junit.jupiter.api.*;

public class ServiceTests {
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
    public void testRegisterUserSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException {
        new UserService(dataAccess).register(newUserData);

        Assertions.assertEquals(dataAccess.getUser(username), newUserData);
    }

    @Test
    public void testRegisterEmptyUsername() {
        newUserData = new UserData("", password, email);

        Assertions.assertThrows(BadRequestException.class, () ->
                new UserService(dataAccess).register(newUserData));
    }

    @Test
    public void testRegisterEmptyPassword() {
        newUserData = new UserData(username, "", email);

        Assertions.assertThrows(BadRequestException.class, () ->
            new UserService(dataAccess).register(newUserData));
    }

    @Test
    public void testRegisterEmptyEmail() {
        newUserData = new UserData(username, password, "");

        Assertions.assertThrows(BadRequestException.class, () ->
            new UserService(dataAccess).register(newUserData));
    }

    @Test
    public void testRegisterUsernameTaken() {
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            new UserService(dataAccess).register(newUserData);
            new UserService(dataAccess).register(newUserData);
        });
    }

    @Test
    public void testLoginSuccess() throws UnauthorizedException, DataAccessException, BadRequestException, AlreadyTakenException {
        new UserService(dataAccess).register(newUserData);
        AuthData authData = new UserService(dataAccess).login(new UserData(username, password, null));
        Assertions.assertEquals(dataAccess.getAuth(authData.authToken()).username(), username);
    }

    @Test
    public void testLoginWrongPassword() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            new UserService(dataAccess).register(newUserData);
            new UserService(dataAccess).login(new UserData(username, "password", null));
        });
    }

    @Test
    public void testLogoutSuccess() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        AuthData authData = new UserService(dataAccess).register(newUserData);
        new UserService(dataAccess).logout(authData.authToken());
        Assertions.assertNull(dataAccess.getAuth(authData.authToken()));
    }

    @Test
    public void testLogoutUnauthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            new UserService(dataAccess).register(newUserData);
            new UserService(dataAccess).logout("fakeAuthToken");
        });
    }

    @Test
    public void testCreateGameSuccess() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        String authToken = new UserService(dataAccess).register(newUserData).authToken();
        int gameID = new GameService(dataAccess).createGame(newGameData, authToken).gameID();
        Assertions.assertEquals(dataAccess.getGame(gameID).game().getBoard(), newGameData.game().getBoard());
    }

    @Test
    public void testCreateGameNullName() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            String authToken = new UserService(dataAccess).register(newUserData).authToken();
            new GameService(dataAccess).createGame(new GameData(1, null, null, "", new ChessGame()), authToken).gameID();
        });
    }

    @Test
    public void testCreateGameUnauthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () ->
            new GameService(dataAccess).createGame(newGameData, "badAuth").gameID()
        );
    }

    @Test
    public void testJoinGameSuccess() throws BadRequestException, DataAccessException, AlreadyTakenException, UnauthorizedException {
        String authToken = new UserService(dataAccess).register(newUserData).authToken();
        int gameID = new GameService(dataAccess).createGame(newGameData, authToken).gameID();
        JoinGameRequest joinReq = new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID);
        new GameService(dataAccess).joinGame(joinReq, authToken);
        Assertions.assertEquals(dataAccess.getGame(gameID).whiteUsername(), username);
    }

    @Test
    public void testJoinGameUnauthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            String authToken = new UserService(dataAccess).register(newUserData).authToken();
            int gameID = new GameService(dataAccess).createGame(newGameData, authToken).gameID();
            JoinGameRequest joinReq = new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID);
            new GameService(dataAccess).joinGame(joinReq, "badAuth");
        });
    }

    @Test
    public void testJoinGameNullColor() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            String authToken = new UserService(dataAccess).register(newUserData).authToken();
            int gameID = new GameService(dataAccess).createGame(newGameData, authToken).gameID();
            JoinGameRequest joinReq = new JoinGameRequest(null, gameID);
            new GameService(dataAccess).joinGame(joinReq, authToken);
        });
    }

    @Test
    public void testJoinGameAlreadyTaken() {
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            String authToken = new UserService(dataAccess).register(newUserData).authToken();
            int gameID = new GameService(dataAccess).createGame(newGameData, authToken).gameID();
            JoinGameRequest joinReq = new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID);
            new GameService(dataAccess).joinGame(joinReq, authToken);
            new GameService(dataAccess).joinGame(joinReq, authToken);
        });
    }

    @Test
    public void testListGamesSuccess() throws UnauthorizedException, DataAccessException, BadRequestException, AlreadyTakenException {
        String authToken = new UserService(dataAccess).register(newUserData).authToken();
        new GameService(dataAccess).createGame(newGameData, authToken);
        new GameService(dataAccess).createGame(newGameData, authToken);
        new GameService(dataAccess).createGame(newGameData, authToken);

        Assertions.assertEquals(new GameService(dataAccess).listGames(authToken), dataAccess.listGames());
    }

    @Test
    public void testListGamesUnauthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            String authToken = new UserService(dataAccess).register(newUserData).authToken();
            new GameService(dataAccess).createGame(newGameData, authToken);
            new GameService(dataAccess).createGame(newGameData, authToken);
            new GameService(dataAccess).createGame(newGameData, authToken);

            new GameService(dataAccess).listGames("badAuth");
        });
    }

}
