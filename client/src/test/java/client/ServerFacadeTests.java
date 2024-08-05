package client;

import dataaccess.DataAccessException;
import models.UserData;
import org.junit.jupiter.api.*;
import server.ResponseException;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    private final String newUsername = "newuser";
    private final String newPassword = "newpass";
    private final String newEmail = "email@email.com";
    private final UserData newUserData = new UserData(newUsername, newPassword, newEmail);

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new ServerFacade("http://localhost:" + port);

    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void resetDatabase() throws ResponseException {
        serverFacade.clearDatabase();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testDeleteDatabase() {
        Assertions.assertThrows(ResponseException.class, () -> {
            serverFacade.register(newUserData);
            serverFacade.clearDatabase();
            serverFacade.login(newUserData);
        });
    }

    @Test
    public void testRegisterUserSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            serverFacade.register(newUserData);
            serverFacade.login(newUserData);
        });
    }

    @Test
    public void testRegisterUserInvalid() {
        Assertions.assertThrows(ResponseException.class, () ->
                serverFacade.register(new UserData(null, "pass", "email@email.com")));
    }

    @Test
    public void testLoginSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            serverFacade.register(newUserData);
            serverFacade.login(newUserData);
                });
    }

    @Test
    public void testLoginInvalid() {
        Assertions.assertThrows(ResponseException.class, () -> {
            serverFacade.register(newUserData);
            serverFacade.login(new UserData(newUsername, "wrongPass", null));
        });
    }

    @Test
    public void testLogoutSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            serverFacade.register(newUserData);
            serverFacade.logout();
        });

    }

}
