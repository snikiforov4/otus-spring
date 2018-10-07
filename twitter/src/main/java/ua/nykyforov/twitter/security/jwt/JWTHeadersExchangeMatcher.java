package ua.nykyforov.twitter.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JWTHeadersExchangeMatcher implements ServerWebExchangeMatcher {

    @Override
    public Mono<MatchResult> matches(final ServerWebExchange exchange) {
        return Mono.just(exchange)
                .map(ServerWebExchange::getRequest)
                .map(ServerHttpRequest::getHeaders)
                .filter(h -> h.containsKey(HttpHeaders.AUTHORIZATION))
                .flatMap($ -> MatchResult.match())
                .switchIfEmpty(MatchResult.notMatch());
    }

}
