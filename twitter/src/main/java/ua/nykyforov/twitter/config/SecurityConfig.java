package ua.nykyforov.twitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import ua.nykyforov.twitter.security.TokenAuthenticationConverter;
import ua.nykyforov.twitter.security.UnauthorizedAuthenticationEntryPoint;
import ua.nykyforov.twitter.security.jwt.JWTHeadersExchangeMatcher;
import ua.nykyforov.twitter.security.jwt.JWTReactiveAuthenticationManager;
import ua.nykyforov.twitter.security.jwt.TokenProvider;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final TokenProvider tokenProvider;

    @Autowired
    public SecurityConfig(ReactiveUserDetailsService reactiveUserDetailsService,
                          TokenProvider tokenProvider) {
        this.reactiveUserDetailsService = reactiveUserDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            UnauthorizedAuthenticationEntryPoint entryPoint) {
        return http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/user/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/tweet/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(webFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter webFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager());
        authenticationWebFilter.setAuthenticationConverter(new TokenAuthenticationConverter(tokenProvider));
        authenticationWebFilter.setRequiresAuthenticationMatcher(new JWTHeadersExchangeMatcher());
        authenticationWebFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return authenticationWebFilter;
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new JWTReactiveAuthenticationManager(reactiveUserDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
