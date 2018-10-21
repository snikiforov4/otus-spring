package ua.nykyforov.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.dto.JwtToken;
import ua.nykyforov.twitter.dto.UserDto;
import ua.nykyforov.twitter.security.jwt.JwtTokenService;


@RestController
public class AuthorizationController {

    private final JwtTokenService tokenService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationController(JwtTokenService tokenService,
                                   ReactiveAuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public Mono<JwtToken> authorize(@RequestBody UserDto userDto) {
        return authenticationManager.authenticate(toAuthentication(userDto))
                .map(auth -> new JwtToken(tokenService.createToken(auth)));
    }

    private Authentication toAuthentication(UserDto userDto) {
        return new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
    }

}
