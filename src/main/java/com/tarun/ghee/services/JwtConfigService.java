package com.tarun.ghee.services;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.tarun.ghee.entity.User.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;

@Component
public class JwtConfigService {
    @Value("${JWT_ISSUER}")
    private String jwtIssuer;

    @Value("${JWT_SECRETKEY}")
    private   String jwtSecretKey;

    @GetMapping
    public String authenticateUser(){
        return "working without spring security";
    }

    public String createJWTtokens(UserModel ownerEntity ){
        Instant now = Instant.now();

        //to add the username and roles in the token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds( 20 * 60 * 1000))
                .subject(ownerEntity.getUsername())
                .subject(ownerEntity.getEmailaddress())
                .claim("role",ownerEntity.getRoles())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return encoder.encode(params).getTokenValue();
    }
}
