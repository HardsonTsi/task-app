package com.hardtech.securityservice.Security;

abstract public class JWTUtils {
    public static final String SECRET = "mySecret1234";
    public static final String PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final long EXPIRATION_ACCESS_TOKEN = 9000 * 60 * 1000;
    public static final long EXPIRATION_REFRESH_TOKEN = 90000 * 60 * 1000;
}
