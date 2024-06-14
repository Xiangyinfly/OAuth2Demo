package com.xiang.demo1.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JwtUtils {

    //设置过期时间1h
    private static final Long EXPIRE = 24 * 60 * 60 * 1000L;
    //设置私钥
    private static final String SECRET_KEY = "T738eZvNyLkp3TYErvSH3UMSrLC5xL7J";
    //密钥
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String getToken(UserDetails userDetails) {
        return Jwts.builder()
                .header()//头信息
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .id(String.valueOf(IdUtil.simpleUUID()))//标识id
                .subject(JSONUtil.toJsonStr(userDetails.getUsername()))//主体
                .expiration(Date.from(Instant.now().plusSeconds(EXPIRE)))//过期时间
                .issuedAt(new Date())//签发时间
                .issuer("xy")//签发者
                .signWith(KEY, Jwts.SIG.HS256)//签名
                .compact();
    }

    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static Boolean isTokenExpired(String token) {
        Date exp = parsePayload(token).get("exp", Date.class);
        if (exp == null) {
            return true;
        }
        return exp.before(Date.from(Instant.now()));
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        String username = (String) parseSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static Object parseSubject(String token) {
        return parsePayload(token).get("sub");
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

}