package nextstep.auth.unit.authentication;

import nextstep.auth.authentication.AuthenticationException;
import nextstep.auth.authentication.AuthenticationFailureHandler;
import nextstep.auth.authentication.DefaultAuthenticationFailureHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DefaultAuthenticationFailureHandlerTest {
    private AuthenticationFailureHandler handler;

    @BeforeEach
    void setUp() {
        handler = new DefaultAuthenticationFailureHandler();
    }

    @Test
    void handle() throws IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        handler.handle(request, response, new AuthenticationException());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}