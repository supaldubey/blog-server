package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.base.repository.BaseRepository;
import in.cubestack.apps.blog.post.domain.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PostRepository extends BaseRepository<Post> {

    @Inject
    EntityManager entityManager;

    public PostRepository(EntityManager entityManager) {
        super(entityManager, Post.class);
    }
}
