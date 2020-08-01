package in.cubestack.apps.blog.counter.service;

import in.cubestack.apps.blog.counter.domain.Counter;
import in.cubestack.apps.blog.counter.domain.CounterType;
import in.cubestack.apps.blog.counter.repo.CounterRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class CounterService {

    @Inject
    private CounterRepository counterRepository;

    public void counter(Long contentId, CounterType counterType) {
        // TODO not good
        Optional<Counter> counter = counterRepository.findCounter(contentId, counterType);
        if(counter.isPresent()) {
            counterRepository.updateCounter(contentId, counterType);
        } else {
            counterRepository.persist(new Counter(contentId, counterType, 1l));
        }
    }
}
