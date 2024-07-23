package dataaccess;

import models.AuthData;
import models.GameData;
import models.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryDAO implements DataAccess{
    private int nextGameID = 1;

    final private HashMap<String, AuthData> auths = new HashMap<>();
    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public void clear() {
        auths.clear();
        games.clear();
        users.clear();
    }

    @Override
    public void createUser(UserData u) throws DataAccessException {
        users.put(u.username(), u);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        GameData newGame = new GameData(nextGameID++, game.whiteUsername(),
                                        game.blackUsername(), game.gameName(), game.game());

        games.put(newGame.gameID(), newGame);
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        if (!games.containsKey(game.gameID())) {
            throw new DataAccessException("Game not found");
        }
        games.replace(game.gameID(), game);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        AuthData newAuth = AuthData.generateAuthData(username);
        auths.put(newAuth.authToken(), newAuth);
        return newAuth;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (!auths.containsKey(authToken)) {
            throw new DataAccessException("authToken does not exist");
        }
        auths.remove(authToken);
    }
}
