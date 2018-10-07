package ua.nykyforov.twitter.security;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.security.jwt.TokenProvider;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


public class TokenAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationConverter.class);
    private static final String BEARER = "Bearer ";
    private static final Predicate<String> MATCH_BEARER_LENGTH = v -> v.length() > BEARER.length();
    private static final Function<String, String> ISOLATE_BEARER_VALUE = v -> v.substring(BEARER.length());

    private final TokenProvider tokenProvider;

    public TokenAuthenticationConverter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
        logger.info("Path={}", serverWebExchange.getRequest().getPath());
        return Mono.justOrEmpty(serverWebExchange)
                .map(TokenAuthenticationConverter::getTokenFromRequest)
                .filter(Objects::nonNull)
                .filter(MATCH_BEARER_LENGTH)
                .map(ISOLATE_BEARER_VALUE)
                .filter(StringUtils::isNotEmpty)
                .map(tokenProvider::getAuthentication)
                .filter(Objects::nonNull);
    }

    private static String getTokenFromRequest(ServerWebExchange serverWebExchange) {
        String token = serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        return Strings.nullToEmpty(token);
    }
}
