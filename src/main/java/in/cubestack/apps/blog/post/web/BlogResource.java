package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;
import in.cubestack.apps.blog.post.service.CategoryService;
import in.cubestack.apps.blog.post.service.PostService;
import in.cubestack.apps.blog.post.service.PostSummary;
import in.cubestack.apps.blog.post.service.TagService;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    private final PostService postService;
    private final EventService eventService;
    private final CategoryService categoryService;
    private final TagService tagService;

    public BlogResource(PostService postService, EventService eventService, CategoryService categoryService, TagService tagService) {
        this.postService = postService;
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance blog();

        public static native TemplateInstance category();

        public static native TemplateInstance tag();

        public static native TemplateInstance post();
    }

    @GET
    @Timed(name = "homeFetchTime", description = "A measure of how long it takes to Fetch home.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance home() {
        return blogPage(Map.of());
    }

    @GET
    @Path("/error")
    public TemplateInstance errorPage() {
        return blogPage(Map.of("renderingError", true));
    }

    @GET
    @Path("/notFound")
    public TemplateInstance notFound() {
        return blogPage(Map.of("notFound", true));
    }

    private TemplateInstance blogPage(Map<String, Object> additionalMeta) {
        var template = Templates
                .blog()
                .data("posts", postService.getAllPostSummaries())
                .data("categories", categoryService.findAll())
                .data("tags", tagService.findAll());

        for (Map.Entry<String, Object> item : additionalMeta.entrySet()) {
            template = template.data(item.getKey(), item.getValue());
        }

        return template;
    }

    @GET
    @Path("category/{slug}")
    @Timed(name = "categoryFetchTime", description = "A measure of how long it takes to Fetch category info.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance category(@PathParam("slug") String slug) {
        List<PostSummary> posts = postService.getPostSummaryByCategorySlug(slug);

        return Templates
                .category()
                .data("notFound", posts.isEmpty())
                .data("slug", slug)
                .data("posts", posts)
                .data("categories", categoryService.findAll())
                .data("tags", tagService.findAll());
    }

    @GET
    @Path("tag/{slug}")
    @Timed(name = "tagFetchTime", description = "A measure of how long it takes to Fetch tag info.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance tag(@PathParam("slug") String slug) {
        List<PostSummary> posts = postService.getPostSummaryByTagSlug(slug);

        return Templates
                .tag()
                .data("notFound", posts.isEmpty())
                .data("slug", slug)
                .data("posts", posts)
                .data("tags", tagService.findAll())
                .data("categories", categoryService.findAll());
    }

    @GET
    @Path("{slug}")
    @Timed(name = "blogFetchTime", description = "A measure of how long it takes to Fetch blog.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance getPostBySlug(@PathParam("slug") String slug) {
        var postSummaryOptional = postService.getSummary(slug);
        if (postSummaryOptional.isPresent()) {
            PostSummary summary = postSummaryOptional.get();
            eventService.trigger(summary.getId(), EventType.POST_VIEWS);

            return Templates
                    .post()
                    .data("post", summary);
        } else {
            return blogPage(Map.of("notFound", true));

        }
    }
}
