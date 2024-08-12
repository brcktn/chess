package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<Integer, Set<Session>> sessions = new ConcurrentHashMap<>();

    public void addSession(int gameId, Session session) {
        sessions.computeIfAbsent(gameId, k -> new HashSet<>());
        sessions.get(gameId).add(session);
        System.out.println("test");
    }

    public void remove(int gameId, Session session) {
        if (sessions.containsKey(gameId)) {
            sessions.get(gameId).remove(session);
        }
    }

    public void broadcast(int gameId, String message) throws IOException {
        for (Session session : sessions.get(gameId)) {
            send(session, message);
        }
    }

    public void sendError(Session session, String message) throws IOException {
        ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
        send(session, new Gson().toJson(error));
    }

    public void send(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getRemote().sendString(message);
        }
    }

    public void clear() {
        sessions.clear();
    }
}
