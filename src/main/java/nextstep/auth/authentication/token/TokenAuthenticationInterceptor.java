package nextstep.auth.authentication.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.auth.authentication.AbstractAuthenticationInterceptor;
import nextstep.auth.authentication.ProviderManager;
import nextstep.auth.token.JwtTokenProvider;

public class TokenAuthenticationInterceptor extends AbstractAuthenticationInterceptor {
    public TokenAuthenticationInterceptor(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper, ProviderManager providerManager) {
        super(new TokenAuthenticationConverter(objectMapper), providerManager, new TokenAuthenticationSuccessHandler(jwtTokenProvider, objectMapper));
    }
}
