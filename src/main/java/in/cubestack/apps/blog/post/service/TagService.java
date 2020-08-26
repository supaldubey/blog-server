package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.admin.resource.TagCandidate;
import in.cubestack.apps.blog.post.domain.Tag;
import in.cubestack.apps.blog.post.repo.TagRepository;
import in.cubestack.apps.blog.util.ContentHelper;
import io.quarkus.cache.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;
    private final ContentHelper contentHelper;

    public TagService(TagRepository tagRepository, ContentHelper contentHelper) {
        this.tagRepository = tagRepository;
        this.contentHelper = contentHelper;
    }

    @CacheResult(cacheName = "tags")
    public List<TagCandidate> findAll() {
        LOGGER.info("Find all tags called");
        return tagRepository.findAll().list()
                .stream().sorted(Comparator.comparing(Tag::getTitle))
                .map(TagCandidate::from).collect(Collectors.toList());
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

}
