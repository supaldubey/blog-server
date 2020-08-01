package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    public List<Post> findAll(
            @QueryParam("categories") List<String> categories,
            @QueryParam("tags") List<String> tags
    ) {
        if(categories != null && categories.size() > 0) {
            return postService.findAllPublishedPostsByCategories(
                    categories
                            .stream()
                            .map(Long::valueOf)
                            .collect(Collectors.toList())
            );
        } else if(tags != null && tags.size() > 0) {
            return postService.findAllPublishedPostsByTags(
                    tags
                            .stream()
                            .map(Long::valueOf)
                            .collect(Collectors.toList())
            );
        }
        return postService.findAllPublished();
    }

    @GET
    @Path("{id}")
    public Post findById(@PathParam("id") Long id) {
        return postService.findById(id);
    }

    @POST
    public Post save(Post post) {
        return postService.save(post);
    }

    @PUT
    public Post update(Post post) {
        return postService.update(post);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        postService.delete(id);
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance post(Post post);
    }

    @GET
    @Path("html")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance post() {
        Post post = new Post(
                new Person("Arun", "Kumar", "bitsevn"),
                "REST APIs with Quarkus RestEasy",
                "Getting started guide",
                null,
                "rest-apis-with-quarkus-resteasy",
                "# REST APIs with Quarkus RestEasy"
        );
        return Templates.post(post);
    }

}
