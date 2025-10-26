package utils;

import classes.users.User;

public record UserVerification (User user, boolean isConfirmed) {}
