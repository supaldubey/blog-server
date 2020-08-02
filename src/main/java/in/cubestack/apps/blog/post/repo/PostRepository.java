package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository implements PanacheRepositoryBase<Post, Long> {

    public Optional<Post> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

    public List<Post> findAllPublishedPostsByCategories(List<Long> categories) {
        return find("select distinct p from Post p left join p.postCategories pc where pc.category.id in ?1 and p.postStatus = ?2", categories, PostStatus.PUBLISHED).list();
    }

    public List<Post> findAllPublishedPostsByTags(List<Long> tags) {
        return find("select distinct p from Post p left join p.postTags pt where pt.tag.id in ?1 and p.postStatus = ?2", tags, PostStatus.PUBLISHED).list();
    }

    public List<Post> findAllByPostStatus(PostStatus postStatus) {
        return find("postStatus", postStatus).list();
    }
}
