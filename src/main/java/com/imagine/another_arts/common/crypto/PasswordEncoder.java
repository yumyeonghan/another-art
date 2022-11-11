package com.imagine.another_arts.common.crypto;

public interface PasswordEncoder {
    String encryptPassword(String rawPassword);
    boolean isMatch(String rawPassword, String hashedPassword);
}
