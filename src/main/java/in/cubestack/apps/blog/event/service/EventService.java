package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;
import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.repo.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventProcessingService eventProcessingService;

    @Transactional
    public void trigger(Long contentId, EventType eventType) {
        Event event = new Event(contentId, eventType);
        eventRepository.persist(event);
        eventProcessingService.process(event);
    }
}
