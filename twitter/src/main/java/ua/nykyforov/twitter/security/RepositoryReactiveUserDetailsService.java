package ua.nykyforov.twitter.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.User;
import ua.nykyforov.twitter.service.UserService;

@Service
public class RepositoryReactiveUserDetailsService implements ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryReactiveUserDetailsService.class);

    private final UserService userService;

    @Autowired
    public RepositoryReactiveUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        logger.debug("Find by username: {}", username);
        return userService.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User Not Found"))))
                .map(User::toUserDetails);
    }
}
