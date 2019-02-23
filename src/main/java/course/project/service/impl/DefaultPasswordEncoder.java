package course.project.service.impl;

import course.project.exception.InternalServerErrorException;
import course.project.service.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {
    @Override
    public String encodePassword(String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerErrorException();
        }
        byte[] hashBytes = messageDigest.digest(password.getBytes());

        StringBuilder encodedPassword = new StringBuilder();
        for (byte b : hashBytes) {
            encodedPassword.append(String.format("%02x", b));
        }

        return encodedPassword.toString();
    }
}
