package in.cubestack.apps.blog.base.web;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.util.Map;

@ApplicationScoped
public class HttpHelper {

    private static final String COOKIE_TOKEN_NAME = "token";
    private static final String AUTHENTICATION_HEADER = "Authentication";
    private static final String BASIC_AUTH_HEADER = "BASIC ";

    public static final int MAX_COOKIE_AGE = 60 * 60 * 60;

    public String getTokenFromCookie(ContainerRequestContext requestContext) {
        return requestContext.getCookies().entrySet().stream().filter(e -> e.getKey().equals(COOKIE_TOKEN_NAME))
                .map(Map.Entry::getValue).map(Cookie::getValue)
                .findFirst().orElse(null);
    }

    public NewCookie createTokenCookie(String token) {
        return new NewCookie(COOKIE_TOKEN_NAME,
                token,
                "/",
                null,
                null,
                MAX_COOKIE_AGE,
                false);
    }

    public String getTokenFromHeader(ContainerRequestContext requestContext) {
        String token = null;
        String authHeaderVal = requestContext.getHeaderString(AUTHENTICATION_HEADER);
        if (authHeaderVal != null) {
            token = authHeaderVal.substring(BASIC_AUTH_HEADER.length());
        }

        return token;
    }

    public String getTokenFromRequest(ContainerRequestContext requestContext) {
        // Find from Cookie
        String token = getTokenFromCookie(requestContext);

        // Try to get from header
        if (token == null) {
            token = getTokenFromHeader(requestContext);
        }
        return token;
    }
}
