package in.cubestack.apps.blog.event.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;


@Entity
@Table(name = "event")
@SequenceGenerator(name = "default_gen", sequenceName = "event_id_seq", allocationSize = 1)
public class Event extends BaseModel {

    @Column
    private Long contentId;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType eventType;


    public Event() {
    }

    public Event(Long contentId, EventType eventType) {
        this.contentId = contentId;
        this.eventType = eventType;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setCounterType(EventType eventType) {
        this.eventType = eventType;
    }

}
