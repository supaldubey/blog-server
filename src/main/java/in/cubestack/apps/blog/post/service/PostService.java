package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.repo.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PostService {

    @Inject
    PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id);
    }

    public Optional<Post> findBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    public List<Post> findAll() {
        return postRepository.findAll().list();
    }

    public List<Post> findAllPublishedPostsByCategories(List<Long> categories) {
        return postRepository.findAllPublishedPostsByCategories(categories);
    }

    public List<Post> findAllPublishedPostsByTags(List<Long> tags) {
        return postRepository.findAllPublishedPostsByTags(tags);
    }

    public List<Post> findAllPublished() {
        return postRepository.findAllByPostStatus(PostStatus.PUBLISHED);
    }

    public List<Post> findAllDrafts() {
        return postRepository.findAllByPostStatus(PostStatus.DRAFT);
    }

    public Post save(Post post) {
        postRepository.persist(post);
        return post;
    }

    public Post update(Post post) {
        postRepository.persist(post);
        return post;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
