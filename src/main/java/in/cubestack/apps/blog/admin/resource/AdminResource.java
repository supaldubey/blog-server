package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.admin.service.AdminService;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.RoleService;
import in.cubestack.apps.blog.core.service.User;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.CategoryService;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.post.service.TagService;
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

    @Inject
    AdminService adminService;

    @Inject
    HttpHelper httpHelper;

    @Inject
    PostService postService;

    @Inject
    PersonService personService;

    @Inject
    CategoryService categoryService;

    @Inject
    RoleService roleService;

    @Inject
    TagService tagService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance login();

        public static native TemplateInstance dashboard();
        public static native TemplateInstance posts();
        public static native TemplateInstance users();
        public static native TemplateInstance tags();
        public static native TemplateInstance roles();
        public static native TemplateInstance categories();
        public static native TemplateInstance createUser();
        public static native TemplateInstance createPost();

        public static native TemplateInstance user();
    }

    @GET
    @Path("dashboard")
    @RolesAllowed("Admin")
    public TemplateInstance dashboard(@Context SecurityContext securityContext) {
        return users(securityContext);
    }

    @GET
    @Path("users")
    @RolesAllowed("Admin")
    public TemplateInstance users(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.users()
                .data("user", user)
                .data("users", personService.findAll());
    }

    @GET
    @Path("posts")
    @RolesAllowed("Admin")
    public TemplateInstance posts(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.posts()
                .data("user", user)
                .data("posts", postService.findAll());
    }

    @GET
    @Path("roles")
    @RolesAllowed("Admin")
    public TemplateInstance roles(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.tags()
                .data("user", user)
                .data("roles", roleService.findAll());
    }

    @GET
    @Path("tags")
    @RolesAllowed("Admin")
    public TemplateInstance tags(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.tags()
                .data("user", user)
                .data("tags", tagService.findAll());
    }

    @GET
    @Path("categories")
    @RolesAllowed("Admin")
    public TemplateInstance categories(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.categories()
                .data("user", user)
                .data("categories", categoryService.findAll());
    }

    @GET
    @Path("users/create")
    @RolesAllowed("Admin")
    public TemplateInstance createUserForm(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        System.out.println("Logged in user: " + user);
        return Templates.createUser()
                .data("user", user)
                .data("roles", roleService.findAll());
    }

    @GET
    @Path("posts/create")
    @RolesAllowed("Admin")
    public TemplateInstance createPostForm(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.createPost()
                .data("user", user);
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
