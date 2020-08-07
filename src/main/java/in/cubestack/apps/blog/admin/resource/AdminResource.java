package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.admin.service.AdminService;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.RoleService;
import in.cubestack.apps.blog.core.service.User;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostType;
import in.cubestack.apps.blog.post.service.CategoryService;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.post.service.TagService;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

        public static native TemplateInstance editPost();

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
                .data("roles", roleService.findAll())
                .data("users", personService.findAll());
    }

    @GET
    @Path("posts")
    @Transactional
    @RolesAllowed("Admin")
    public TemplateInstance posts(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        List<PostCandidate> postCandidates = postService.findAll().stream()
                .map(PostCandidate::from).collect(Collectors.toList());

        return Templates.posts()
                .data("user", user)
                .data("posts", postCandidates);
    }

    @GET
    @Path("role")
    @RolesAllowed("Admin")
    public TemplateInstance roles(@Context SecurityContext securityContext) {
        User user = (User) securityContext.getUserPrincipal();
        return Templates.roles()
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
    @Path("categories/list")
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
                .data("user", user)
                .data("postTypes", PostType.values())
                .data("categories", categoryService.findAll())
                .data("tags", tagService.findAll());
    }

    @GET
    @Path("posts/edit/{postId}")
    @Transactional
    @RolesAllowed("Admin")
    public TemplateInstance editPost(@Context SecurityContext securityContext, @PathParam("postId") Long postId) {
        User user = (User) securityContext.getUserPrincipal();

        Post post = postService.findById(postId).orElseThrow();
        PostCandidate postCandidate = PostCandidate.from(post);
        return Templates.editPost()
                .data("user", user)
                .data("post", postCandidate)
                .data("postTypes", PostType.values())
                .data("categories", categoryService.findAll())
                .data("postStatues", PostStatus.values())
                .data("tags", tagService.findAll());
    }

    @GET
    @Path("/login")
    public TemplateInstance login(@QueryParam("invalid") String invalid) {
        return Templates.login().data("invalid", invalid == null ? "" : invalid);
    }

    @POST
    @Path("/posts")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("Admin")
    public Response persistPost(@Context UriInfo uriInfo,
                                @Context SecurityContext securityContext,
                                @Form PostCandidate postCandidate) {
        User user = (User) securityContext.getUserPrincipal();
        postService.persistPost(user, postCandidate);

        URI dashboardUri = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/posts")
                .build();
        return Response.seeOther(dashboardUri).build();
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
