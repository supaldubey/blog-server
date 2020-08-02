package in.cubestack.apps.blog.comment.web;

import in.cubestack.apps.blog.comment.domain.Comment;
import in.cubestack.apps.blog.comment.service.CommentService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Inject
    CommentService commentService;

    @GET
    public List<Comment> findAll() {
        return commentService.findAll();
    }
}
