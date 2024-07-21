package server;

import dataaccess.*;
import handler.ClearHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import handler.RegisterHandler;
import spark.*;

public class Server {
    private final DataAccess dataAccess = new MemoryDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your server.endpoints and handle exceptions here.
        Spark.delete("/db",      this::clearDatabase);
        Spark.post(  "/user",    this::registerUser);
        Spark.post(  "/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get(   "/game",    this::listGames);
        Spark.post(  "/game",    this::createGame);
        Spark.put(   "/game",    this::joinGame);

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
            return "{ \"message\": \"Error: bad request\" }";
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: (description of error)\" }";
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
        return null;
    }

    private Object createGame(Request req, Response res) {
        return null;
    }

    private Object joinGame(Request req, Response res) {
        return null;
    }

}
