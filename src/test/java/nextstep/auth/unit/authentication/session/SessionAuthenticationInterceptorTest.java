package nextstep.auth.unit.authentication.session;

import nextstep.auth.authentication.*;
import nextstep.auth.authentication.session.SessionAuthenticationInterceptor;
import nextstep.auth.unit.authentication.MockAuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SessionAuthenticationInterceptorTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private SessionAuthenticationInterceptor interceptor;

    @BeforeEach
    void setUp() {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);

        UserDetails expectedUserDetails = new DefaultUserDetails(EMAIL, PASSWORD);
        Mockito.when(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(expectedUserDetails);

        ProviderManager providerManager = new ProviderManager(Collections.singletonList(new UsernamePasswordAuthenticationProvider(userDetailsService)));
        interceptor = new SessionAuthenticationInterceptor(providerManager);
    }

    @Test
    void success() throws IOException {
        HttpServletRequest request = MockAuthenticationRequest.createSessionRequest(EMAIL, PASSWORD);
        MockHttpServletResponse response = new MockHttpServletResponse();

        interceptor.preHandle(request, response, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void fail() throws IOException {
        HttpServletRequest request = MockAuthenticationRequest.createSessionRequest(EMAIL + "test", PASSWORD);
        MockHttpServletResponse response = new MockHttpServletResponse();

        interceptor.preHandle(request, response, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
