package server;

import com.google.gson.Gson;
import models.UserData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;


    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private UserData login(UserData userData) throws ResponseException {
        return this.makeRequest("POST", "/session", userData, UserData.class);
    }

    private void logout() throws ResponseException {
        this.makeRequest("DELETE", "/session", null, null);
    }



    private <T> T makeRequest(String method, String path, T req, Class<T> responseType) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path).toURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);

            if (req != null) {
                connection.addRequestProperty("Content-Type", "application/json");
                String reqData = new Gson().toJson(req);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(reqData.getBytes());
                }
            }

            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new ResponseException(statusCode, connection.getResponseMessage());
            }

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
            throw new ResponseException(500, e.getMessage());
        }
    }
}
