package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class EventProcessingService {

    @Inject
    AnalyticsGeneratorService analyticsGeneratorService;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    public void process(Event event) {
        executorService.submit(() -> analyticsGeneratorService.ingest(event));
    }
}
