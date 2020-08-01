package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepositoryBase<Post, Long> {

    public Post findBySlug(String slug) {
        return find("slug", slug).firstResult();
    }

    public List<Post> findByPostStatus(PostStatus postStatus) {
        return find("postStatus", postStatus).list();
    }
}
