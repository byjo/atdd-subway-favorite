package nextstep.auth.authentication;

import nextstep.auth.context.Authentication;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractAuthenticationInterceptor implements HandlerInterceptor {
    private final AuthenticationConverter converter;
    private final ProviderManager providerManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public AbstractAuthenticationInterceptor(AuthenticationConverter converter, ProviderManager providerManager, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this(converter, providerManager, authenticationSuccessHandler, new DefaultAuthenticationFailureHandler());
    }

    public AbstractAuthenticationInterceptor(AuthenticationConverter converter, ProviderManager providerManager, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        this.converter = converter;
        this.providerManager = providerManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            AuthenticationToken token = converter.convert(request);
            Authentication authentication = providerManager.authenticate(token);
            authenticationSuccessHandler.handle(request, response, authentication);
        } catch (AuthenticationException e) {
            authenticationFailureHandler.handle(request, response, e);
        }

        return false;
    }
}
