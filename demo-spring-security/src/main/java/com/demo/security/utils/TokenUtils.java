package com.demo.security.utils;

public class TokenUtils {

    private static String TOKEN = "";
    private static String USERNAME = "";
    private static String USER_ID = "";

    public static String getToken() {
        return TOKEN;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static void setToken(String token) {
        TOKEN = token;
    }

    public static void setUsername(String username) {
        USERNAME = username;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static void setUserId(String userId) {
        USER_ID = userId;
    }
}
