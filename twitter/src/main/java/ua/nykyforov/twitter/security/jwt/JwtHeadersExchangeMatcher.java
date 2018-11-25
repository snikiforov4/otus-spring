package ua.nykyforov.twitter.security.jwt;

import com.google.common.collect.ImmutableList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtHeadersExchangeMatcher implements ServerWebExchangeMatcher {

    @Override
    public Mono<MatchResult> matches(final ServerWebExchange exchange) {
        return Mono.just(exchange)
                .map(ServerWebExchange::getRequest)
                .map(ServerHttpRequest::getHeaders)
                .flatMapIterable(h -> h.getOrDefault(HttpHeaders.AUTHORIZATION, ImmutableList.of()))
                .any(h -> h.startsWith("Bearer"))
                .filter($ -> $)
                .flatMap($ -> MatchResult.match())
                .switchIfEmpty(MatchResult.notMatch());
    }

}
