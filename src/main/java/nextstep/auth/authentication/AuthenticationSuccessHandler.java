package nextstep.auth.authentication;

import nextstep.auth.context.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationSuccessHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException;
}
