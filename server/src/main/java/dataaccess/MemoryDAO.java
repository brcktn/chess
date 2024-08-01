package dataaccess;

import models.AuthData;
import models.GameData;
import models.ListGamesResponse;
import models.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryDAO implements DataAccess{
    private int nextGameID = 1;

    final private HashMap<String,  AuthData> auths = new HashMap<>();
    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashMap<String,  UserData> users = new HashMap<>();


    @Override
    public void clear() {
        auths.clear();
        games.clear();
        users.clear();
    }
    @Override
    public void createUser(UserData userData) {
        users.put(userData.username(), userData);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public GameData createGame(GameData gameData) {
        GameData newGame = new GameData(nextGameID++, gameData.whiteUsername(),
                                        gameData.blackUsername(), gameData.gameName(), gameData.game());

        games.put(newGame.gameID(), newGame);
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public ListGamesResponse listGames() {
        return new ListGamesResponse(games.values());
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        if (!games.containsKey(game.gameID())) {
            throw new DataAccessException("Game not found");
        }
        games.replace(game.gameID(), game);
    }

    @Override
    public AuthData createAuth(String username) {
        AuthData newAuth = AuthData.generateAuthData(username);
        auths.put(newAuth.authToken(), newAuth);
        return newAuth;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (!auths.containsKey(authToken)) {
            throw new DataAccessException("authToken does not exist");
        }
        auths.remove(authToken);
    }

    @Override
    public boolean checkPassword(UserData user) {
        return (Objects.equals(user.password(), users.get(user.username()).password()));
    }
}
