package ua.nykyforov.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.dto.UserDto;
import ua.nykyforov.twitter.security.jwt.JWTToken;
import ua.nykyforov.twitter.security.jwt.TokenProvider;


@RestController
public class AuthorizationController {

    private final TokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationController(TokenProvider tokenProvider,
                                   ReactiveAuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public Mono<JWTToken> authorize(@RequestBody UserDto userDto) {
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());

        Mono<Authentication> authentication = this.authenticationManager.authenticate(authenticationToken);
        authentication.doOnError(e -> {throw new BadCredentialsException("Bad credentials", e);});
        ReactiveSecurityContextHolder.withAuthentication(authenticationToken);

        return authentication.map(auth -> {
            String jwt = tokenProvider.createToken(auth);
            return new JWTToken(jwt);
        });
    }

}
