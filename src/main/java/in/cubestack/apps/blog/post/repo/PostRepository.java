package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostSummary;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    @Inject
    EntityManager entityManager;

    public Optional<Post> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

    public List<Post> findAllPublishedPostsByCategories(List<Long> categories) {
        return find("select distinct p from Post p left join p.postCategories pc where pc.category.id in ?1 and p.postStatus = ?2", categories, PostStatus.PUBLISHED).list();
    }

    public List<Post> findAllPublishedPostsByTags(List<Long> tags) {
        return find("select distinct p from Post p left join p.postTags pt where pt.tag.id in ?1 and p.postStatus = ?2", tags, PostStatus.PUBLISHED).list();
    }

    public List<PostSummary> getPostSummary() {
        Query nativeQuery = entityManager.createNativeQuery("select p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, a.firstName, a.lastName, a.userName, pa.likes, pa.views from post p inner join person a on a.id = p.authorId inner join postAnalytics pa on pa.postId = p.id  ", "PostViewMapping");
        return nativeQuery.getResultList();
    }

    public List<Post> findAllByPostStatus(PostStatus postStatus) {
        return find("postStatus", postStatus).list();
    }
}
