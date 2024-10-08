package server;

import dataaccess.*;
import handler.*;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UnauthorizedException;
import spark.*;
import websocket.WebSocketHandler;

import java.io.IOException;

public class Server {
    private DataAccess dataAccess;
    private WebSocketHandler webSocketHandler;

    public int run(int desiredPort) {
        try {
            dataAccess = new MySqlDAO();
            webSocketHandler = new WebSocketHandler(dataAccess);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your server.endpoints and handle exceptions here.

        Spark.webSocket("/ws", webSocketHandler);

        Spark.delete("/db",      this::clearDatabase);
        Spark.post(  "/user",    this::registerUser);
        Spark.post(  "/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get(   "/game",    this::listGames);
        Spark.post(  "/game",    this::createGame);
        Spark.put(   "/game",    this::joinGame);
        Spark.delete("/game", this::leaveGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearDatabase(Request req, Response res) {
        try {
            new ClearHandler(dataAccess).clear();
            res.status(200);
            return "{}";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object registerUser(Request req, Response res) {
        try {
            res.status(200);
            return new RegisterHandler(dataAccess).register(req);
        } catch (BadRequestException e) {
            res.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        } catch (AlreadyTakenException e) {
            res.status(403);
            return "{ \"message\": \"Error: username taken\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error\" }";
        }
    }

    private Object login(Request req, Response res) {
        try {
            res.status(200);
            return new LoginHandler(dataAccess).login(req);
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object logout(Request req, Response res) {
        try {
            res.status(200);
            new LogoutHandler(dataAccess).logout(req);
            return "{}";
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object listGames(Request req, Response res) {
        try {
            res.status(200);
            return new ListGamesHandler(dataAccess).listGames(req);
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object createGame(Request req, Response res) {
        try {
            res.status(200);
            return new CreateGameHandler(dataAccess).createGame(req);
        } catch (BadRequestException e) {
            res.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object joinGame(Request req, Response res) {
        try {
            res.status(200);
            new JoinGameHandler(dataAccess).joinGame(req);
            return "{}";
        } catch (BadRequestException e) {
            res.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (AlreadyTakenException e) {
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

    private Object leaveGame(Request req, Response res) {
        try {
            res.status(200);
            new LeaveGameHandler(dataAccess).leaveGame(req);
            return "{}";
        } catch (BadRequestException e) {
            res.status(400);
            return "";
        } catch (UnauthorizedException e) {
            res.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (AlreadyTakenException e) {
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "";
        }
    }

}
