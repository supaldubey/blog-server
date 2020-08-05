package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.util.ContentHelper;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Path("author")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @Inject
    ContentHelper contentHelper;

    @Inject
    PostService postService;

    @CheckedTemplate
    public static class Templates {

        public static native TemplateInstance author(List<Post> posts);
    }

    @GET
    @Path("{username: .+}")
    public TemplateInstance authorPosts(@PathParam("username") String username) throws IOException, URISyntaxException {
        List<Post> posts = Collections.EMPTY_LIST;
        // TODO
        return AuthorResource.Templates.author(posts);
    }
}
