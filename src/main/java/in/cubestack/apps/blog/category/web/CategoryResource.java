package in.cubestack.apps.blog.category.web;

import in.cubestack.apps.blog.category.domain.Category;
import in.cubestack.apps.blog.category.service.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GET
    @Path("id")
    public Category findOne(@PathParam("id") Long id) {
        return categoryService.findOne(id);
    }

    @POST
    public Category save(Category category) {
        return categoryService.save(category);
    }

    @PUT
    public Category update(Category category) {
        return categoryService.update(category);
    }

    @GET
    @Path("id")
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }


    @GET
    @Path("post-counts")
    public Map<Category, Long> findCategoryPostCounts() {
        return categoryService.findCategoryPostCounts();
    }


}
