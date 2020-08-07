package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.admin.resource.TagCandidate;
import in.cubestack.apps.blog.post.domain.Tag;
import in.cubestack.apps.blog.post.repo.TagRepository;
import in.cubestack.apps.blog.util.ContentHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Inject
    ContentHelper contentHelper;

    public List<TagCandidate> findAll() {
        return tagRepository.findAll().list().stream().map(TagCandidate::from).collect(Collectors.toList());
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

    public TagCandidate save(TagCandidate candidate) {
        Tag tag = candidate.toNewTag();
        tag.setSlug(contentHelper.slugify(tag.getTitle()));
        tagRepository.persist(tag);
        return TagCandidate.from(tag);
    }

    private Supplier<UnsupportedOperationException> unsupportedOpsSupplier() {
        return () -> new UnsupportedOperationException("Not yet implemented");
    }

    public Map<Tag, Long> findTagPostCounts() {
        return new HashMap<>();
    }
}
