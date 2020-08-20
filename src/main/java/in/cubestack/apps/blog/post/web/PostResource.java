package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.admin.resource.PostCandidate;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("posts")
@RolesAllowed("Admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GET
    public List<PostCandidate> findAll(
            @QueryParam("categories") List<String> categories,
            @QueryParam("tags") List<String> tags) {

        if (categories != null && categories.size() > 0) {
            return postService.findAllPublishedPostsByCategories(
                    categories
                            .stream()
                            .map(Long::valueOf)
                            .collect(Collectors.toList())
            );
        } else if (tags != null && tags.size() > 0) {
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
        return postService.findById(id).orElseThrow(() -> new RuntimeException("No post found for ID: " + id));
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

}
