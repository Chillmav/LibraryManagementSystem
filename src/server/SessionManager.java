package server;

import classes.Library;
import classes.users.User;
import handlers.UserHandler;
import utils.SessionResult;
import utils.UserSession;

import java.sql.Connection;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    ConcurrentHashMap<UUID, UserSession> sessionIdMap = new ConcurrentHashMap<>();

    public SessionResult handleSession(Request request, User user) throws Exception {

        UUID uuid  = request.getUserId();

        if (uuid != null) {

            return new SessionResult(sessionIdMap.get(uuid), uuid);

        } else {

            UUID uuid1 = UUID.randomUUID();
            UserSession us = new UserSession(user, Duration.of(300, ChronoUnit.SECONDS));
            sessionIdMap.put(uuid1, us);
            return new SessionResult(us, uuid1);

        }

    }

    public ConcurrentHashMap<UUID, UserSession> getSessionIdMap() {
        return sessionIdMap;
    }
}
