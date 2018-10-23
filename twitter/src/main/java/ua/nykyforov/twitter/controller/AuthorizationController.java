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
import ua.nykyforov.twitter.dto.JwtToken;
import ua.nykyforov.twitter.dto.UserDto;
import ua.nykyforov.twitter.security.jwt.JwtTokenService;


@RestController
public class AuthorizationController {

    private final JwtTokenService tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationController(JwtTokenService tokenProvider,
                                   ReactiveAuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public Mono<JwtToken> authorize(@RequestBody UserDto userDto) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword());
        return this.authenticationManager.authenticate(authenticationToken)
                .doOnError(e -> {
                    throw new BadCredentialsException("Bad credentials", e);
                })
                .doOnNext(auth -> ReactiveSecurityContextHolder.withAuthentication(authenticationToken))
                .map(auth -> new JwtToken(tokenProvider.createToken(auth)));
    }

}
