package server;

import dataaccess.DataAccessException;
import spark.*;

public class Server {


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your server.endpoints and handle exceptions here.
        Spark.delete("/db", this::clearDatabase);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearDatabase(Request req, Response res) throws DataAccessException {
        return "";
    }

    private Object registerUser(Request req, Response res) throws DataAccessException {
        return null;
    }

    private Object login(Request req, Response res) throws DataAccessException {
        return null;
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        return null;
    }

    private Object listGames(Request req, Response res) throws DataAccessException {
        return null;
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        return null;
    }

    private Object joinGame(Request req, Response res) throws DataAccessException {
        return null;
    }

}
