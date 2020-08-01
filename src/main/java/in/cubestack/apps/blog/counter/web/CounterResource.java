package in.cubestack.apps.blog.counter.web;

import in.cubestack.apps.blog.counter.domain.CounterType;
import in.cubestack.apps.blog.counter.service.CounterService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("counters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CounterResource {

    @Inject
    private CounterService counterService;

    @PUT
    public void counter(@QueryParam("contentId") Long contentId, @QueryParam("counterType") String counterType) {
        counterService.counter(contentId, CounterType.of(counterType));
    }
}
