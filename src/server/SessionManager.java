package server;

import classes.Library;
import classes.users.User;
import handlers.UserHandler;
import utils.SessionResult;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    ConcurrentHashMap<UUID, User> sessionIdMap = new ConcurrentHashMap<>();

    public SessionResult handleSession(Request request, User user) throws Exception {

        UUID uuid  = request.getUserId();

        if (uuid != null) {

            return new SessionResult(sessionIdMap.get(uuid), uuid);

        } else {

            UUID uuid1 = UUID.randomUUID();
            sessionIdMap.put(uuid1, user);
            return new SessionResult(user, uuid1);



        }


    }


}
