package ua.nykyforov.twitter.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.security.jwt.JwtTokenService;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


public class TokenAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationConverter.class);

    private static final String PREFIX = "Bearer ";
    private static final Predicate<String> MATCH_HEADER_LENGTH = v -> v.length() > PREFIX.length();
    private static final Function<String, String> TO_BARE_TOKEN = v -> v.substring(PREFIX.length()).trim();

    private final JwtTokenService tokenProvider;

    public TokenAuthenticationConverter(JwtTokenService tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
        logger.debug("Path={}", serverWebExchange.getRequest().getPath());
        return Mono.justOrEmpty(serverWebExchange)
                .map(TokenAuthenticationConverter::getTokenFromRequest)
                .filter(Objects::nonNull)
                .filter(MATCH_HEADER_LENGTH)
                .map(TO_BARE_TOKEN)
                .filter(StringUtils::isNotEmpty)
                .map(tokenProvider::getAuthentication)
                .filter(Objects::nonNull);
    }

    private static String getTokenFromRequest(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
    }
}
