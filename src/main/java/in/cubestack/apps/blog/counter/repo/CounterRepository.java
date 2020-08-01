package in.cubestack.apps.blog.counter.repo;

import in.cubestack.apps.blog.counter.domain.Counter;
import in.cubestack.apps.blog.counter.domain.CounterType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class CounterRepository implements PanacheRepositoryBase<Counter, Long> {

    public Optional<Counter> findCounter(Long contentId, CounterType counterType) {
        return find("contentId = ?1 and counterType = ?2", contentId, counterType).firstResultOptional();
    }

    public void updateCounter(Long contentId, CounterType counterType) {
        update("counterValue = counterValue+1 where contentId = ?1 and counterType = ?2", contentId, counterType);
    }
}
