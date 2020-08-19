package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostAnalytics;
import in.cubestack.apps.blog.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AnalyticsGeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsGeneratorService.class);

    private final PostService postService;

    public AnalyticsGeneratorService(PostService postService) {
        this.postService = postService;
    }

    public void ingest(Event event) {
        LOGGER.info("Ingesting event {}", event);
        switch (event.getEventType()) {
            case POST_LIKES:
            case POST_VIEWS:
                ingestPostEvent(event);
                return;
            default:
        }
    }

    private void doIngestPostEvent(Event event) {
        Post post = postService.findById(event.getContentId()).orElseThrow(() -> new RuntimeException("No post found for ID: " + event.getContentId()));
        PostAnalytics postAnalytics = post.getPostAnalytics();

        switch (event.getEventType()) {
            case POST_VIEWS:
                postAnalytics.viewed();
                return;
            case POST_LIKES:
                postAnalytics.liked();
                return;
            default:
                throw new RuntimeException("Invalid event type:  " + event.getEventType());
        }
    }

    private void ingestPostEvent(Event event) {
        try {
            doIngestPostEvent(event);
        } catch (Exception ex) {
            LOGGER.error("Failed updating event", ex);
        }
    }
}
