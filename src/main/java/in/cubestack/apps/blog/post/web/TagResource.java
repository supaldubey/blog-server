package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.admin.resource.AdminResource;
import in.cubestack.apps.blog.admin.resource.TagCandidate;
import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;
import in.cubestack.apps.blog.post.domain.Tag;
import in.cubestack.apps.blog.post.service.TagService;
import org.jboss.resteasy.annotations.Form;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("tags")
@RolesAllowed("Admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    private final TagService tagService;
    private final EventService eventService;

    public TagResource(TagService tagService, EventService eventService) {
        this.tagService = tagService;
        this.eventService = eventService;
    }

    @GET
    public List<TagCandidate> findAll() {
        return tagService.findAll();
    }

    @GET
    @Path("{id}")
    public Tag findOne(@PathParam("id") Long id) {
        return tagService.findOne(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public TagCandidate save(@Form @Valid TagCandidate tag) {
        return tagService.save(tag);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response save(@Context UriInfo uriInfo, @Form @Valid TagCandidate candidate) {
        var savedTag = tagService.save(candidate);

        if (candidate.getTagId() == 0) {
            eventService.trigger(savedTag.getTagId(), EventType.TAG_CREATED);
        } else {
            eventService.trigger(savedTag.getTagId(), EventType.TAG_UPDATED);
        }


        URI dashboardUri = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/tags")
                .build();
        return Response.seeOther(dashboardUri).build();
    }

    @PUT
    public Tag update(Tag tag) {
        return tagService.update(tag);
    }

    @GET
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        tagService.delete(id);
    }

    @GET
    @Path("post-counts")
    public Map<Tag, Long> findTagPostCounts() {
        return tagService.findTagPostCounts();
    }

}
