package com.example.parking_system_orange_innovation.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 7000;
    public static final String JWT_SECRET = "klgfhdjklghfjkdhgjkfdhgjjsdokjfljkdfhgurdshnghjuvugjkdhbgjkdgksahfnskf" +
            "lkldfhgjkdfshfurdsilkfjkldsngkhfgljkdskljgikfjlkdngvbjghfdgndjckhbgjkfnjdghljdxkngjshdnljg";

    static byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
    public static final Key JWT_KEY = Keys.hmacShaKeyFor(keyBytes);
}
