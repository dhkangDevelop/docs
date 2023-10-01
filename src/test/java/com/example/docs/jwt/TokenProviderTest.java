package com.example.docs.jwt;

import com.example.docs.app.v1.auth.account.Account;
import com.example.docs.app.v1.auth.account.req.CreateAccountRequest;
import com.example.docs.global.dto.AccountDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenProviderTest {
    @Test
    void makeJJWT() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); //or HS384 or HS512
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("secretString > "+secretString);

        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        SecretKey returnKey = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("returnKey > " + Encoders.BASE64.encode(returnKey.getEncoded()));

        long now = (new Date()).getTime();
        Date validity = new Date(now + 2000);

        String userName = "dhkang";
        AccountDTO accountDTO = new AccountDTO(Account.createFirstGeneralUser(new CreateAccountRequest("dhkang", "1234")));
        String authorization = Jwts.builder().setSubject(userName).claim("Authorization", accountDTO).signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
        System.out.println("authorization > " + authorization);

        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(authorization);
        String signature = claimsJws.getSignature();
        Claims body = claimsJws.getBody();
        JwsHeader header = claimsJws.getHeader();

        System.out.println("signature > " + signature);
        System.out.println("body > " + body);
        System.out.println("header > " + header.toString());
    }
}
