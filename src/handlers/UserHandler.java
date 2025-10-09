package handlers;

import server.Request;
import utils.JsonUtils;

public class UserHandler {

    public static void handleLogin(Request req) {


        String body = req.getBody();

        JsonUtils.createMapFromJson(body);


    }

    public static void handleRegister(Request req) {


        String body = req.getBody();

    }
}
