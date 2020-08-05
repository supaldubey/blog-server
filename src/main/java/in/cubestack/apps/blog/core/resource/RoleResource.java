package in.cubestack.apps.blog.core.resource;

import in.cubestack.apps.blog.core.service.RoleService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {

    @Inject
    RoleService roleService;

    @POST
    public Response save(@Context UriInfo uriInfo, RoleCandidate candidate) {
        roleService.save(candidate);
        return Response.created(uriInfo.getRequestUri()).build();
    }

    @GET()
    public List<RoleCandidate> findAll() {
        return roleService.findAll();
    }
}
