package com.example.demo.utils;

import io.jsonwebtoken.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;
import java.util.UUID;

/**
 * @author Chu
 * @create 2022-03-11-21:06
 */
public class JwtUtils {
    private static String signature = "admin";
    private static long time = 1000*60*60*24;
    public static String jwt(String email,String password){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("email",email)
                .claim("password",password)
                //主题
                .setSubject("")
                //有效时间
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }
    public static String parseEmail(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String email = (String)body.get("email");
        body.get("password");
        return email;
    }

}
