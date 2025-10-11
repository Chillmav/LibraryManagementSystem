package utils;

import classes.users.User;

import java.util.UUID;

public record SessionResult (User user, UUID uuid) {}

