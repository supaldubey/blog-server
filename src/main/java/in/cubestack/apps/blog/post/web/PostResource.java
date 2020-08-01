package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    public List<Post> findAll() {
        return postService.findAll();
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
}
