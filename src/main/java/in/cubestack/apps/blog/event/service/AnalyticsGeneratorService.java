package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostAnalytics;
import in.cubestack.apps.blog.post.service.PostService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AnalyticsGeneratorService {

    @Inject
    PostService postService;

    public void ingest(Event event) {
        switch (event.getEventType()) {
            case POST_LIKES:
            case POST_VIEWS:
                ingestPostEvent(event);
                return;
            default:
        }
    }

    private void ingestPostEvent(Event event) {
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
}
