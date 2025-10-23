package utils;

import classes.users.User;

import java.time.Duration;
import java.time.Instant;

public class UserSession {

    private final User user;
    private final Duration duration;
    private final Instant creationTime;

    public UserSession(User user, Duration duration) {
        this.user = user;
        this.creationTime = Instant.now();
        this.duration = duration;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(creationTime.plus(duration));
    }
    public long getRemainingTime() {

        Duration remainingTime = Duration.between(Instant.now(), creationTime.plus(duration));
        return Math.max(remainingTime.toSeconds(), 0);
    }

    public User getUser() {
        return user;
    }
};
