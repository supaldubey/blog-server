package in.cubestack.apps.blog.core.resource;

import in.cubestack.apps.blog.admin.resource.AdminResource;
import in.cubestack.apps.blog.core.service.RoleService;
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

@Path("roles")
@RolesAllowed("Admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {

    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response save(@Context UriInfo uriInfo, @Form @Valid RoleCandidate candidate) {
        roleService.save(candidate);

        URI dashboardUri = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/role")
                .build();
        return Response.seeOther(dashboardUri).build();
    }

    @GET()
    public List<RoleCandidate> findAll() {
        return roleService.findAll();
    }
}
