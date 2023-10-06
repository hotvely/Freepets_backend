package com.kh.Freepets.security;

import com.kh.Freepets.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider
{
    private static final String SECRET_KEY = "FlRpX30MqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M10icegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(Member member)
    {
        Date expriryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));


        return Jwts.builder()
                .setSubject(member.getId())
                .setIssuer("Freepets")
                .setIssuedAt(new Date())
                .setExpiration(expriryDate)
                .claim("name", member.getName())
                .claim("nickName", member.getNickname())
                .claim("email", member.getEmail())
                .claim("phone", member.getPhone())
                .claim("address", member.getAddress())
                .claim("createAccountDate", member.getCreateAccountDate())
                .claim("authority", member.getAuthority())
                .claim("birth", member.getBirth())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims validateAndGetClaims(String token)
    {

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateAndGetUserId(String token)
    {
        // Claims 쓰려면 스프링부트 버전 3.0.0으로 낮춰야 함;;;
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getAuthorityFromToken(String token)
    {
        // Claims 쓰려면 스프링부트 버전 3.0.0으로 낮춰야 함;;;
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("Authority", String.class);
    }

}
