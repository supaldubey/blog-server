package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.admin.resource.AdminResource;
import in.cubestack.apps.blog.admin.resource.CategoryCandidate;
import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;
import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.service.CategoryService;
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

@Path("categories")
@RolesAllowed("Admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private final CategoryService categoryService;
    private final EventService eventService;

    public CategoryResource(CategoryService categoryService, EventService eventService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @GET
    public List<CategoryCandidate> findAll() {
        return categoryService.findAll();
    }

    @GET
    @Path("{id}")
    public Category findOne(@PathParam("id") Long id) {
        return categoryService.findOne(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response save(@Context UriInfo uriInfo, @Form @Valid CategoryCandidate category) {

        var savedCandidate = categoryService.save(category);

        if (category.getId() == 0) {
            eventService.trigger(savedCandidate.getId(), EventType.CATEGORY_CREATED);
        } else {
            eventService.trigger(savedCandidate.getId(), EventType.CATEGORY_UPDATED);
        }

        URI dashboardUri = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/categories")
                .build();
        return Response.seeOther(dashboardUri).build();
    }

    @PUT
    public Category update(Category category) {
        return categoryService.update(category);
    }

    @GET
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }

}
