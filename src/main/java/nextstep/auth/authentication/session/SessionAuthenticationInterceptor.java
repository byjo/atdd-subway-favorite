package nextstep.auth.authentication.session;

import nextstep.auth.authentication.AbstractAuthenticationInterceptor;
import nextstep.auth.authentication.ProviderManager;

public class SessionAuthenticationInterceptor extends AbstractAuthenticationInterceptor {
    public SessionAuthenticationInterceptor(ProviderManager providerManager) {
        super(new SessionAuthenticationConverter(), providerManager, new SessionAuthenticationSuccessHandler());
    }
}
