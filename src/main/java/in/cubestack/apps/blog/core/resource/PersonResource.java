package in.cubestack.apps.blog.core.resource;


import in.cubestack.apps.blog.core.service.PersonService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("persons")
@RolesAllowed("Admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @POST
    public Response save(@Context UriInfo uriInfo, @Valid PersonCandidate candidate) {
        personService.save(candidate);
        return Response.created(uriInfo.getRequestUri()).build();
    }

    @GET()
    public List<PersonCandidate> findAll() {
        return personService.findAll();
    }

}
