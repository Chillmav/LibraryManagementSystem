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
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionManager {

    ConcurrentHashMap<UUID, UserSession> sessionIdMap = new ConcurrentHashMap<>();


    public SessionManager() {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::checkAndExpire, 0, 1, TimeUnit.MINUTES);

    }

    public SessionResult handleSession(Request request, User user) {

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

    private void checkAndExpire() {

        sessionIdMap.entrySet().removeIf(entry -> entry.getValue().isExpired());

    }
}
