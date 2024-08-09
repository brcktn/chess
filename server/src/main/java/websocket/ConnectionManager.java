package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<Integer, Set<Session>> sessions = new ConcurrentHashMap<>();

    public void addSession(int gameId, Session session) {
        if (sessions.containsKey(gameId)) {
            sessions.put(gameId, new HashSet<>());
        }
        sessions.get(gameId).add(session);
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

    private void send(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getRemote().sendString(message);
        }
    }
}
