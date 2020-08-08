package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.util.ContentHelper;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("blog")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    @Inject
    ContentHelper contentHelper;

    @Inject
    PostService postService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance post();

        public static native TemplateInstance postView1(Post post);

        public static native TemplateInstance postView2(Post post);
    }

    @GET
    @Path("{slug}")
    public TemplateInstance getPostBySlug(@PathParam("slug") String slug) {
        return Templates
                .post()
                .data("post", postService.findBySlug(slug));
    }
}
