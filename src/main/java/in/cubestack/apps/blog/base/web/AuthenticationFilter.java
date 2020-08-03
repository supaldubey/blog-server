package in.cubestack.apps.blog.base.web;

import in.cubestack.apps.blog.core.service.TokenAuthenticationService;
import in.cubestack.apps.blog.core.service.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
@Provider
@PreMatching
@RegisterForReflection
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Context
    UriInfo uriInfo;

    @Inject
    HttpHelper httpHelper;

    @Inject
    TokenAuthenticationService tokenAuthenticationService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = httpHelper.getTokenFromRequest(requestContext);

        if (token != null) {
            prepareSecurityContext(requestContext, token);
        }
    }

    private void prepareSecurityContext(ContainerRequestContext requestContext, String token) {
        try {
            addSecurityContext(requestContext, token);
        } catch (Exception ex) {
            LOGGER.error("Error parsing tokens", ex);
        }
    }

    private void addSecurityContext(ContainerRequestContext requestContext, String token) {
        Optional<User> userOptional = tokenAuthenticationService.fromToken(token);
        if (!userOptional.isPresent()) {
            return;
        }

        User user = userOptional.get();
        addUserToContext(requestContext, user);
    }

    private void addUserToContext(ContainerRequestContext requestContext, User user) {
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return user;
            }

            @Override
            public boolean isUserInRole(String role) {
                return user.getRoles().contains(role);
            }

            @Override
            public boolean isSecure() {
                return uriInfo.getAbsolutePath().toString().startsWith("https");
            }

            @Override
            public String getAuthenticationScheme() {
                return BASIC_AUTH;
            }
        });
    }
}