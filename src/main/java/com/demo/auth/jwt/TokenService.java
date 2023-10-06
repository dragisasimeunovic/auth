package com.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class TokenService {
  
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
}
