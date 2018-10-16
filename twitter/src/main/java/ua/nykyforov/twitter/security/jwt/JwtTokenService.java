package ua.nykyforov.twitter.security.jwt;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
public class JwtTokenService {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private static final String SALT_KEY = "JpxM4e858rc673syopdZnMFb*ExeqJtUc0HJ_iOxu~jiSYu+yPdPw93OBBjF";
    private static final long TOKEN_VALIDITY = HOURS.toMillis(24);
    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication) {
        checkNotNull(authentication, "authentication");
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(Date.from(Instant.now().plusMillis(TOKEN_VALIDITY)))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        if (StringUtils.isEmpty(token) || !validateToken(token)) {
            throw new BadCredentialsException("Invalid token");
        }
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",", -1))
                        .filter(StringUtils::isNotEmpty)
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.info("Invalid JWT signature.");
            logger.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token.");
            logger.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token.");
            logger.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT token.");
            logger.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
