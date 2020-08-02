package in.cubestack.apps.blog.event.repo;

import in.cubestack.apps.blog.event.domain.Event;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, Long> {

}
