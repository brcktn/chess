package dataaccess;

import models.AuthData;
import models.GameData;
import models.ListGamesResponse;
import models.UserData;

import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDAO implements DataAccess {

    public MySqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        executeUpdate("TRUNCATE TABLE authData");
        executeUpdate("TRUNCATE TABLE gameData");
        executeUpdate("TRUNCATE TABLE userData");
    }

    @Override
    public void createUser(UserData u) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ListGamesResponse listGames() throws DataAccessException {
        return null;
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
            gameJson VARCHAR(255) NOT NULL,
            PRIMARY KEY (gameID)
        );
        """
    };

    private final String[] createUserStatement = {
        """
        CREATE TABLE IF NOT EXISTS userData (
            username VARCHAR(255) NOT NULL,
            hash VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL,
            PRIMARY KEY (username)
        );
        """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createAuthStatement) {
                try (var stmt = conn.prepareStatement(statement)) {
                    stmt.execute();
                }
            }
            
            for (var statement : createGameStatement) {
                try (var stmt = conn.prepareStatement(statement)) {
                    stmt.execute();
                }
            }

            for (var statement : createUserStatement) {
                try (var stmt = conn.prepareStatement(statement)) {
                    stmt.execute();
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private int executeUpdate(String sql, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof AuthData p) ps.setString(i + 1, p.toString());
                    else if (param instanceof GameData p) ps.setString(i + 1, p.toString());
                    else if (param instanceof UserData p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

}
