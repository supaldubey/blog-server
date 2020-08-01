package in.cubestack.apps.blog.tag.web;

import in.cubestack.apps.blog.tag.domain.Tag;
import in.cubestack.apps.blog.tag.service.TagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    @Inject
    private TagService tagService;

    @GET
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GET
    @Path("id")
    public Tag findOne(@PathParam("id") Long id) {
        return tagService.findOne(id);
    }

    @POST
    public Tag save(Tag tag) {
        return tagService.save(tag);
    }

    @PUT
    public Tag update(Tag tag) {
        return tagService.update(tag);
    }

    @GET
    @Path("id")
    public void delete(@PathParam("id") Long id) {
        tagService.delete(id);
    }

    @GET
    @Path("post-counts")
    public Map<Tag, Long> findTagPostCounts() {
        return tagService.findTagPostCounts();
    }

}
