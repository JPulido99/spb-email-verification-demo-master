package com.dailycodework.sbemailverificationdemo.reset.password;

import java.util.UUID;

public class TokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}