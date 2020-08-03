package in.cubestack.apps.blog.event.web;

import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.service.EventService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("counters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    @Inject
    EventService eventService;

    @PUT
    public void triggerEvent(@QueryParam("contentId") Long contentId, @QueryParam("eventType") String eventType) {
        eventService.trigger(contentId, EventType.of(eventType));
    }
}
