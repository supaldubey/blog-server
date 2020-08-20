package in.cubestack.apps.blog.event.service;

import io.quarkus.cache.CacheInvalidate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheInvalidationService {

    @CacheInvalidate(cacheName = "posts")
    public void invalidatePosts() {
    }

    @CacheInvalidate(cacheName = "tags")
    public void invalidateTags() {
    }

    @CacheInvalidate(cacheName = "categories")
    public void invalidateCategories() {
    }

    @CacheInvalidate(cacheName = "category-slug")
    public void invalidatePostCategories() {
    }

    @CacheInvalidate(cacheName = "tag-slug")
    public void invalidatePostTags() {
    }

}
