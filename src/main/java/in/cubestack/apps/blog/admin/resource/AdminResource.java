package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.admin.service.AdminService;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.service.User;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/admin")
public class AdminResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance login();
        public static native TemplateInstance dashboard();
    }

    @Inject
    AdminService adminService;

    @Inject
    HttpHelper httpHelper;


    @GET
    @Path("dashboard")
    @RolesAllowed("Admin")
    public TemplateInstance dashboard(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.dashboard().data("user", user);
    }

    @GET
    @Path("/login")
    public TemplateInstance login(@QueryParam("invalid") String invalid) {
        return Templates.login().data("invalid", invalid == null ? "" : invalid);
    }

    @POST
    @Path("/login")
    public Response doLogin(@Context UriInfo uriInfo, @FormParam("username") String username, @FormParam("password") String password) {
        try {
            String token = adminService.login(username, password);
            URI dashboardUri = uriInfo.getBaseUriBuilder()
                    .path(AdminResource.class)
                    .path("/dashboard")
                    .build();
            return Response.seeOther(dashboardUri).cookie(httpHelper.createTokenCookie(token)).build();
        } catch (Exception ex) {
            LOGGER.error("Error authenticating", ex);

            URI loginUri = uriInfo.getBaseUriBuilder()
                    .path(AdminResource.class)
                    .path("/login")
                    .queryParam("invalid", true)
                    .build();
            return Response.seeOther(loginUri).build();
        }
    }
}
