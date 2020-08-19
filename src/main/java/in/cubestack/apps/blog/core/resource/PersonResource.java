package in.cubestack.apps.blog.core.resource;


import in.cubestack.apps.blog.admin.resource.AdminResource;
import in.cubestack.apps.blog.core.service.PersonService;
import org.jboss.resteasy.annotations.Form;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@ApplicationScoped
@Path("persons")
@RolesAllowed("Admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response save(@Context UriInfo uriInfo, @Form @Valid PersonCandidate candidate) {
        personService.save(candidate);

        URI dashboardUri = uriInfo.getBaseUriBuilder()
                .path(AdminResource.class)
                .path("/users")
                .build();
        return Response.seeOther(dashboardUri).build();
    }

    @GET()
    public List<PersonCandidate> findAll() {
        return personService.findAll();
    }

}
