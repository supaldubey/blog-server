package in.cubestack.apps.blog.tag.service;

import in.cubestack.apps.blog.post.repo.PostTagRepository;
import in.cubestack.apps.blog.tag.domain.Tag;
import in.cubestack.apps.blog.tag.repo.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ApplicationScoped
public class TagService {

    @Inject
    private TagRepository tagRepository;

    @Inject
    private PostTagRepository postTagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll().list();
    }

    public Tag findOne(Long id) {
        return tagRepository.findById(id);
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag update(Tag tag) {
        tagRepository.persist(tag);
        return tag;
    }

    public Tag save(Tag tag) {
        tagRepository.persist(tag);
        return tag;
    }

    private Supplier<UnsupportedOperationException> unsupportedOpsSupplier() {
        return () -> new UnsupportedOperationException("Not yet implemented");
    }

    public Map<Tag, Long> findTagPostCounts() {
        return postTagRepository.findTagPostCounts();
    }
}
