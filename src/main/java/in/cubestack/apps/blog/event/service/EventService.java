package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;
import in.cubestack.apps.blog.event.domain.EventType;
import in.cubestack.apps.blog.event.repo.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class EventService {

    private final EventRepository eventRepository;
    private final EventProcessingService eventProcessingService;

    public EventService(EventRepository eventRepository, EventProcessingService eventProcessingService) {
        this.eventRepository = eventRepository;
        this.eventProcessingService = eventProcessingService;
    }

    @Transactional
    public void trigger(Long contentId, EventType eventType) {
        Event event = new Event(contentId, eventType);
        eventRepository.persist(event);
        eventProcessingService.process(event);
    }
}
