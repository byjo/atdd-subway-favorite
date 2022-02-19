package nextstep.auth.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationFailureHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException;
}
