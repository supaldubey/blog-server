package in.cubestack.apps.blog.core.resource;


import in.cubestack.apps.blog.base.web.BlogResponse;
import in.cubestack.apps.blog.base.web.HttpHelper;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.Role;
import in.cubestack.apps.blog.core.service.AuthenticationService;
import in.cubestack.apps.blog.core.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("persons")
public class PersonResource {

    @Inject
    PersonService personService;

    @Inject
    HttpHelper httpHelper;

    @Inject
    AuthenticationService authenticationService;


    public PersonResource(PersonService personService) {
        this.personService = personService;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void save() {
        Random random = new Random();
        Person person = new Person("Random : " + random.nextDouble(), "Random: " + random.nextDouble(), "random_" + random.nextInt());
        personService.save(person);
    }

    @GET
    @Path("/mock-create")
    public void save(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("roles") List<String> roles) {
        Random random = new Random();
        Person person = new Person("Random : " + random.nextDouble(), "Random: " + random.nextDouble(), username);
        person.updatePassword(password);
        for(String role: roles) {
            person.addRole(new Role(role));
        }
        personService.save(person);
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        List<PersonCandidate> personCandidates = personService.findAll()
                .stream()
                .map(PersonCandidate::from)
                .collect(Collectors.toList());

        BlogResponse blogResponse = BlogResponse.withData(personCandidates);
        return Response.ok(blogResponse).build();
    }

}
