package ua.nykyforov.twitter.controller;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.User;
import ua.nykyforov.twitter.dto.UserDto;
import ua.nykyforov.twitter.service.UserService;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("WeakerAccess")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    UserService userService;
    @MockBean
    PasswordEncoder passwordEncoder;

    private WebTestClient webClient;

    @BeforeEach
    void setup() {
        this.webClient = WebTestClient
                .bindToServer()
                .baseUrl(String.format("http://localhost:%d", port))
                .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userService, passwordEncoder);
    }

    @Nested
    @DisplayName("POST /")
    @SuppressWarnings("UnassignedFluxMonoInstance")
    class RegisterNewUser {

        @Test
        void shouldSaveEntity() {
            String username = "username";
            String rawPassword = "password";
            String encodedPassword = "encPassword";
            UserDto userDto = new UserDto(username, rawPassword);
            doReturn(encodedPassword).when(passwordEncoder).encode(eq(rawPassword));
            doReturn(Mono.just(new User(username, encodedPassword, Sets.newHashSet())))
                    .when(userService).save(any(User.class));

            UserDto response = webClient.post()
                    .uri("/user/")
                    .body(BodyInserters.fromObject(userDto))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(UserDto.class)
                    .returnResult().getResponseBody();

            assertThat(response).isNotNull()
                    .satisfies(u -> {
                        assertThat(u.getUsername()).isEqualTo(username);
                        assertThat(u.getPassword()).isEqualTo(encodedPassword);
                    });
            verify(userService, times(1)).save(argThat(u ->
                    u != null && Objects.equals(username, u.getUsername())
                            && Objects.equals(encodedPassword, u.getPassword())
            ));
        }

    }


}