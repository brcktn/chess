package server;

import com.google.gson.Gson;
import models.*;
import ui.ChessClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;
    private final ChessClient chessClient;


    public ServerFacade(String serverUrl, ChessClient chessClient) {
        this.serverUrl = serverUrl;
        this.chessClient = chessClient;
    }

    public void clearDatabase()  throws ResponseException {
        makeRequest("DELETE", "/db", null, null, null);
    }

    public AuthData login(UserData req) throws ResponseException {
        return makeRequest("POST", "/session", req, null, AuthData.class);
    }

    public void logout() throws ResponseException {
        makeRequest("DELETE", "/session", null, chessClient.getAuthToken(), null);
    }

    public AuthData register(UserData req) throws ResponseException {
        return makeRequest("POST", "/user", req, null, AuthData.class);
    }

    public ListGamesResponse listGames() throws ResponseException {
        return makeRequest("GET", "/game", null, chessClient.getAuthToken(), ListGamesResponse.class);
    }

    public void joinGame(JoinGameRequest req) throws ResponseException {
        makeRequest("PUT", "/game", req, chessClient.getAuthToken(), null);
    }

    public GameData createGame(GameData req) throws ResponseException {
        return makeRequest("POST", "/game", req, chessClient.getAuthToken(), GameData.class);
    }

    private <T> T makeRequest(String method, String path, Object req, String header, Class<T> responseType) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path).toURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);

            if (header != null) {
                connection.setRequestProperty("Authorization", header);
            }

            if (req != null) {
                connection.addRequestProperty("Content-Type", "application/json");
                String reqData = new Gson().toJson(req);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(reqData.getBytes());
                }
            }

            connection.connect();
//            int statusCode = connection.getResponseCode();
//            if (statusCode != 200) {
//                throw new ResponseException(statusCode, connection.getResponseMessage());
//            }

            T response = null;
            if (connection.getContentLength() < 0) {
                try (InputStream is = connection.getInputStream()) {
                    InputStreamReader isr = new InputStreamReader(is);
                    if (responseType != null) {
                        response = new Gson().fromJson(isr, responseType);
                    }
                }
            }

            return response;

        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
}
