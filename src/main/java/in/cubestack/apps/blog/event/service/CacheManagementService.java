package in.cubestack.apps.blog.event.service;

import in.cubestack.apps.blog.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagementService.class);

    private final CacheInvalidationService cacheInvalidationService;

    public CacheManagementService(CacheInvalidationService cacheInvalidationService) {
        this.cacheInvalidationService = cacheInvalidationService;
    }

    public void manageCache(Event event) {
        LOGGER.info("Managing cache for event {}", event);

        switch (event.getEventType()) {
            case POST_CREATED:
            case POST_UPDATED:
                cacheInvalidationService.invalidatePosts();
                cacheInvalidationService.invalidatePostCategories();
                cacheInvalidationService.invalidatePostTags();
                break;

            case TAG_CREATED:
            case TAG_UPDATED:
                cacheInvalidationService.invalidateTags();
                break;

            case CATEGORY_CREATED:
            case CATEGORY_UPDATED:
                cacheInvalidationService.invalidateCategories();
                break;
        }
    }


}
