package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;
import in.cubestack.apps.blog.post.service.CategoryService;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.post.service.TagService;
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

    @Inject
    EventService eventService;

    @Inject
    CategoryService categoryService;

    @Inject
    TagService tagService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance blog();

        public static native TemplateInstance post();
    }

    @GET
    public TemplateInstance home() {
        return Templates
                .blog()
                .data("posts", postService.findAllPublished())
                .data("categories", categoryService.findAll())
                .data("tags", tagService.findAll());
    }

    @GET
    @Path("{slug}")
    @Timed(name = "blogFetchTime", description = "A measure of how long it takes to Fetch blog.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance getPostBySlug(@PathParam("slug") String slug) {
        var summary = postService.getSummary(slug);
        eventService.trigger(summary.getId(), EventType.POST_VIEWS);

        return Templates
                .post()
                .data("post", summary);
    }
}
