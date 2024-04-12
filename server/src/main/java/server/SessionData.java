package server;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.Set;

public record SessionData(Map<Session, String> seshes){


    @Override
    public Map<Session, String> seshes() {
        return seshes;
    }
}
