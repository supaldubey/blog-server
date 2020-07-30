package in.cubestack.apps.blog.core.resource;


import in.cubestack.apps.blog.base.web.BlogResponse;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.service.PersonService;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("persons")
public class PersonResource {

    @Inject
    PersonService personService;


    public PersonResource(PersonService personService) {
        this.personService = personService;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public void save() {
        Random random = new Random();
        Person person = new Person("Random : " + random.nextDouble(), "Random: " + random.nextDouble(), "random_" + random.nextDouble());
        personService.save(person);
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response listAll() {
        List<PersonCandidate> personCandidates = personService.findAll()
                .stream()
                .map(PersonCandidate::from)
                .collect(Collectors.toList());

        BlogResponse blogResponse = BlogResponse.withData(personCandidates);
        return Response.ok(blogResponse).build();
    }

}
