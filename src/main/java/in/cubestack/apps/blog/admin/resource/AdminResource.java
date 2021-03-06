package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.admin.service.AdminService;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.RoleService;
import in.cubestack.apps.blog.core.service.User;
import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostType;
import in.cubestack.apps.blog.post.service.CategoryService;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.post.service.TagService;
import in.cubestack.apps.blog.post.web.BlogResource;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/admin")
public class AdminResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    private final AdminService adminService;
    private final HttpHelper httpHelper;
    private final PostService postService;
    private final PersonService personService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final TagService tagService;
    private final EventService eventService;

    public AdminResource(AdminService adminService, HttpHelper httpHelper, PostService postService, PersonService personService, CategoryService categoryService, RoleService roleService, TagService tagService, EventService eventService) {
        this.adminService = adminService;
        this.httpHelper = httpHelper;
        this.postService = postService;
        this.personService = personService;
        this.categoryService = categoryService;
        this.roleService = roleService;
        this.tagService = tagService;
        this.eventService = eventService;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance login();
        public static native TemplateInstance posts();
        public static native TemplateInstance users();
        public static native TemplateInstance tags();
        public static native TemplateInstance roles();
        public static native TemplateInstance categories();
        public static native TemplateInstance createUser();
        public static native TemplateInstance createPost();
        public static native TemplateInstance editPost();
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
                .data("users", personService.findAll())
                .data("activeTab", "users");
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
        return Templates.login().data("invalid", invalid == null ? "" : invalid).data("login", "login");
    }

    @GET
    @RolesAllowed("Admin")
    @Path("/preview/{slug}")
    public TemplateInstance preview(@Context UriInfo uriInfo, @PathParam("slug") String slug) {
        var summary = postService.getSummary(slug).orElseThrow();
        eventService.trigger(summary.getId(), EventType.POST_VIEWS);

        return BlogResource.Templates
                .post()
                .data("baseUrl", uriInfo.getBaseUriBuilder().build())
                .data("post", summary);
    }

    @POST
    @Path("/posts")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed("Admin")
    public Response persistPost(@Context UriInfo uriInfo,
                                @Context SecurityContext securityContext,
                                @Form PostCandidate postCandidate) {
        User user = (User) securityContext.getUserPrincipal();
        Post post = postService.persistPost(user, postCandidate);

        if (postCandidate.getId() == null) {
            eventService.trigger(post.getId(), EventType.POST_CREATED);
        } else {
            eventService.trigger(post.getId(), EventType.POST_UPDATED);
        }

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
