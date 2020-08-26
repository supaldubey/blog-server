package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
