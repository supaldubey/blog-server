package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.repo.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class PostService {

    @Inject
    private PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll().list();
    }

    public Post save(Post post) {
        postRepository.persistAndFlush(post);
        return post;
    }

    public Post update(Post post) {
        postRepository.persistAndFlush(post);
        return post;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
