package utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class testMain {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        System.out.println(PasswordUtils.hashPassword("admin123"));

    }
}
