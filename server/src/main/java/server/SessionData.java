package server;

import org.eclipse.jetty.websocket.api.Session;

public record SessionData(String username, Session session) {
}
