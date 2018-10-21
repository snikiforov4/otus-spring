package ua.nykyforov.twitter.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nullable;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        if (authentication.isAuthenticated()) {
            logger.info("username={} skip authentication", authentication.getName());
            return Mono.just(authentication);
        }
        logger.info("username={} authenticate", authentication.getName());
        return Mono.just(authentication)
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(this::toUserDetails)
                .publishOn(Schedulers.parallel())
                .onErrorResume(this::raiseBadCredentials)
                .filter(u -> passwordEncoder.matches((String) authentication.getCredentials(), u.getPassword()))
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
    }

    private <T> Mono<T> raiseBadCredentials() {
        return raiseBadCredentials(null);
    }

    private <T> Mono<T> raiseBadCredentials(@Nullable Throwable e) {
        return Mono.error(new BadCredentialsException("Invalid Credentials", e));
    }

    private Mono<UserDetails> toUserDetails(final UsernamePasswordAuthenticationToken authenticationToken) {
        String username = authenticationToken.getName();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            return this.userDetailsService.findByUsername(username);
        }
        return null;
    }
}
