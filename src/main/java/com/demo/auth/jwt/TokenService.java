package com.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class TokenService {
  
  // 60 minutes * 60 seconds * 1000 milliseconds
  private static final long ONE_HOUR = 60L * 60L * 1000L;
  
  @Value("${auth.jwt.secret.key}")
  private String secretKey;
  
  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getClaims(token);
    return claimsResolver.apply(claims);
  }
  
  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigninKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
  
  public String getUsername(String token) {
    return getClaim(token, Claims::getSubject);
  }
  
  private Key getSigninKey() {
    byte[] bytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(bytes);
  }
  
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }
  
  public String generateToken(Map<String, Objects> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = getUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }
  
  private boolean isTokenExpired(String token) {
    return getTokenExpiration(token).before(new Date());
  }
  
  private Date getTokenExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }
}
