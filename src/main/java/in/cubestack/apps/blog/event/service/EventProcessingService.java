package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class EventProcessingService {

    private final AnalyticsGeneratorService analyticsGeneratorService;
    private final CacheManagementService cacheManagementService;

    private ExecutorService executorService;

    public EventProcessingService(AnalyticsGeneratorService analyticsGeneratorService, CacheManagementService cacheManagementService) {
        this.analyticsGeneratorService = analyticsGeneratorService;
        this.cacheManagementService = cacheManagementService;
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    public void process(Event event) {
        executorService.submit(() -> handleEvent(event));
    }

    private void handleEvent(Event event) {
        analyticsGeneratorService.ingest(event);
        cacheManagementService.manageCache(event);
    }
}
