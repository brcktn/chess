package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.AuthData;
import models.GameData;
import models.ListGamesResponse;
import models.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlDAO implements DataAccess {

    public MySqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.addBatch("TRUNCATE TABLE authData");
            stmt.addBatch("TRUNCATE TABLE gameData");
            stmt.addBatch("TRUNCATE TABLE userData");

            stmt.executeBatch();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        String sql = "INSERT INTO userData (username, hash, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(userData.password(), BCrypt.gensalt());
            pstmt.setString(1, userData.username());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, userData.email());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT * FROM userData WHERE username = ?;";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("hash");
                String email = rs.getString("email");

                return new UserData(username, hash, email);
            }

            return null;

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        String sql = "INSERT INTO gameData (whiteUsername, blackUsername, gameName, gameJson) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            if (game.game() == null) {
                throw new DataAccessException("Invalid game");
            }
            Gson gson = new Gson();
            String gameJson = gson.toJson(game.game());

            pstmt.setString(1, game.whiteUsername());
            pstmt.setString(2, game.blackUsername());
            pstmt.setString(3, game.gameName());
            pstmt.setString(4, gameJson);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int gameId = rs.getInt(1);

            return new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        String sql = "SELECT * FROM gameData WHERE gameID = ?;";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, gameID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String json = rs.getString("gameJson");

                Gson gson = new Gson();
                ChessGame game = gson.fromJson(json, ChessGame.class);

                return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
            }

            return null;

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public ListGamesResponse listGames() throws DataAccessException {
        String sql = "";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            Collection<GameData> games = new ArrayList<>();

            while (rs.next()) {
                int gameID = rs.getInt("gameID");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String json = rs.getString("gameJson");

                Gson gson = new Gson();
                ChessGame game = gson.fromJson(json, ChessGame.class);
                games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
            }

            return new ListGamesResponse(games);

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }


    private final String[] createAuthStatement = {
        """
        CREATE TABLE IF NOT EXISTS authData (
            authToken VARCHAR(64) UNIQUE NOT NULL,
            username VARCHAR(64) UNIQUE NOT NULL,
            PRIMARY KEY (authToken)
        );
        """
    };

    private final String[] createGameStatement = {
        """
        CREATE TABLE IF NOT EXISTS gameData (
            gameID INTEGER NOT NULL AUTO_INCREMENT,
            whiteUsername VARCHAR(255),
            blackUsername VARCHAR(255),
            gameName VARCHAR(255),
            gameJson JSON NOT NULL,
            PRIMARY KEY (gameID)
        );
        """
    };

    private final String[] createUserStatement = {
        """
        CREATE TABLE IF NOT EXISTS userData (
            username VARCHAR(255) UNIQUE NOT NULL,
            hash VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL,
            PRIMARY KEY (username)
        );
        """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            for (String statement : createAuthStatement) {
                stmt.execute(statement);
            }

            for (String statement : createGameStatement) {
                stmt.execute(statement);
            }

            for (String statement : createUserStatement) {
                stmt.execute(statement);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

}
