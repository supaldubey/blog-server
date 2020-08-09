package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.post.service.PostService;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("blog")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    @Inject
    PostService postService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance post();
    }

    @GET
    @Path("{slug}")
    @Timed(name = "blogFetchTime", description = "A measure of how long it takes to Fetch blog.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance getPostBySlug(@PathParam("slug") String slug) {
        return Templates
                .post()
                .data("post", postService.getSummary(slug));
    }
}
