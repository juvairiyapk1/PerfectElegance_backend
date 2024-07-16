package com.perfectElegance.service;

import com.perfectElegance.modal.User;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final String SECRET_KEY="c04b9b40b404bd2c0caa54a038aaf436df359b79fc99d960962969e42f062428";


    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username=extractUsername(token);
        return (username.equals(user.getUsername())&& !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T>resolver){
        Claims claims =extractAllClaims(token);
        return resolver.apply(claims);
    }

    private  Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("role",user.getRole())
                .claim("isSubscribed" ,user.isSubscribed())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey(){
        byte [] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private final Set<String> blacklistedTokens=new HashSet<>();

    public void invalidToken(String token){
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlackListed(String token){
        return blacklistedTokens.contains(token);
    }




}


