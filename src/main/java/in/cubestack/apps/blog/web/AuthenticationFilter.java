package in.cubestack.apps.blog.web;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Priority(Priorities.AUTHENTICATION)
@Provider
@PreMatching
@RegisterForReflection
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeaderVal = requestContext.getHeaderString("Auth-Token");
        String subject = "supal"; //execute custom authentication
        final SecurityContext securityContext = requestContext.getSecurityContext();

        System.out.println("Security context is " + securityContext);
        System.out.println("This url is " + uriInfo.getAbsolutePath().toString());

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> subject;
            }

            @Override
            public boolean isUserInRole(String role) {
                System.out.println("Checking roles with " + role);
                List<String> roles = Arrays.asList("admin", "Crap", "hello");
                return roles.contains(role);
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