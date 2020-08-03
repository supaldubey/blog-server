package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.admin.service.AdminService;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.User;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;
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
import java.util.List;

@Path("/admin")
public class AdminResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    @Inject
    AdminService adminService;

    @Inject
    HttpHelper httpHelper;

    @Inject
    PostService postService;

    @Inject
    PersonService personService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance login();

        public static native TemplateInstance dashboard();

        public static native TemplateInstance user();
    }

    @GET
    @Path("dashboard")
    @RolesAllowed("Admin")
    public TemplateInstance dashboard(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.dashboard().data("user", user);
    }

    @GET
    @Path("users")
    @RolesAllowed("Admin")
    public TemplateInstance users(@Context SecurityContext securityContext, @QueryParam("created") String created) {
        User user = (User) securityContext.getUserPrincipal();
        List<Person> people = personService.findAll();
        return Templates.user().data("user", user).data("people", people).data("created", created == null ? "" : created);
    }

    @POST
    @Path("/users")
    @RolesAllowed("Admin")
    public Response createUser(@Context UriInfo uriInfo,
                               @FormParam("firstName") String firstName,
                               @FormParam("lastName") String lastName,
                               @FormParam("password") String password,
                               @FormParam("username") String username) {

        personService.createPerson(firstName, lastName, password, username);
        URI users = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/users")
                .queryParam("created", true)
                .build();
        return Response.seeOther(users).build();
    }

    @GET
    @Path("/login")
    public TemplateInstance login(@QueryParam("invalid") String invalid) {
        return Templates.login().data("invalid", invalid == null ? "" : invalid);
    }

    @POST
    @Path("/posts")
    @RolesAllowed("Admin")
    public Response createPost(@Context UriInfo uriInfo,
                               @Context SecurityContext securityContext,
                               @FormParam("title") String title,
                               @FormParam("metatitle") String metatitle,
                               @FormParam("summary") String summary,
                               @FormParam("content") String content) {
        User user = (User) securityContext.getUserPrincipal();
        Post post = postService.createPost(user, title, metatitle, summary, content);

        String path = uriInfo.getBaseUri().toString();
        return Response.ok("Created blog with id " + post.getId() + "View from " + path + "/blog/posts/view2?postId=" + post.getId()).build();
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
