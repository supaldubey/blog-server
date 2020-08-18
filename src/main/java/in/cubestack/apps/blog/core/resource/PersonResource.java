package in.cubestack.apps.blog.core.resource;


import in.cubestack.apps.blog.admin.resource.AdminResource;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.Role;
import in.cubestack.apps.blog.core.service.PersonService;
import org.jboss.resteasy.annotations.Form;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

    @Inject
    PersonService personService;

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

    @GET
    @Transactional
    @Path("mock-crete")
    public Response mockCreate(@Context UriInfo uriInfo,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("username") String username,
                               @QueryParam("password") String password) {

        PersonCandidate personCandidate = new PersonCandidate();
        personCandidate.setFirstName(firstName);
        personCandidate.setLastName(lastName);
        personCandidate.setUsername(username);
        personCandidate.setPassword(password);

        Person person = personService.save(personCandidate);
        person.addRole(new Role("Admin"));

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
