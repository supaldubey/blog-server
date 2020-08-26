package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.admin.resource.PostCandidate;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.core.service.PersonService;
import in.cubestack.apps.blog.core.service.User;
import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostType;
import in.cubestack.apps.blog.post.domain.Tag;
import in.cubestack.apps.blog.post.repo.PostRepository;
import in.cubestack.apps.blog.util.ContentHelper;
import io.quarkus.cache.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final PersonService personService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final ContentHelper contentHelper;

    public PostService(PostRepository postRepository, PersonService personService, CategoryService categoryService, TagService tagService, ContentHelper contentHelper) {
        this.postRepository = postRepository;
        this.personService = personService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.contentHelper = contentHelper;
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findByIdOptional(id);
    }

    public List<PostCandidate> findAll() {
        return toCandidates(postRepository.findAll().list());
    }


    public List<PostCandidate> findAllPublishedPostsByTags(List<Long> tags) {
        return toCandidates(postRepository.findAllPublishedPostsByTags(tags));
    }

    @CacheResult(cacheName = "posts")
    public List<PostSummary> getAllPostSummaries() {
        LOGGER.info("Find all posts called");

        return sortedSummary(postRepository.getAllPostSummaries());
    }

    private List<PostSummary> sortedSummary(List<PostSummary> postSummaries) {
        return postSummaries.stream()
                .sorted(Comparator.comparing(PostSummary::getPublishedAt).reversed())
                .collect(Collectors.toList());
    }

    private List<PostCandidate> toCandidates(List<Post> posts) {
        return posts.stream().map(this::toCandidate).collect(Collectors.toList());
    }

    private PostCandidate toCandidate(Post post) {
        PostCandidate candidate = PostCandidate.from(post);
        candidate.setHtmlContent(contentHelper.markdownToHtml(candidate.getContent()));

        if(candidate.getSummary() != null) {
            candidate.setHtmlSummary(contentHelper.markdownToHtml(candidate.getSummary()));
        }
        return candidate;
    }

    public Post save(Post post) {
        postRepository.persist(post);
        return post;
    }

    public Post update(Post post) {
        postRepository.persist(post);
        return post;
    }

    public Optional<PostSummary> getSummary(String slug) {
        return findSummaryForSlug(slug);
    }

    private Optional<PostSummary> findSummaryForSlug(String slug) {
        try {
            PostSummary postSummary = postRepository.getPostSummary(slug);
            postSummary.setHtmlContent(contentHelper.markdownToHtml(postSummary.getContent()));
            if(postSummary.getSummary() != null) {
                postSummary.setHtmlSummary(contentHelper.markdownToHtml(postSummary.getSummary()));
            }
            return Optional.of(postSummary);
        } catch (Exception ignore) {
            // These is mostly page not found
        }
        return Optional.empty();
    }

    @CacheResult(cacheName = "category-slug")
    public List<PostSummary> getPostSummaryByCategorySlug(String slug) {
        LOGGER.info("Find by cat called for slug {}", slug);

        List<PostSummary> postSummaries = postRepository.getPostSummaryByCategorySlug(slug);
        postSummaries.forEach(ps -> ps.setHtmlContent(contentHelper.markdownToHtml(ps.getContent())));

        return sortedSummary(postSummaries);
    }

    @CacheResult(cacheName = "tag-slug")
    public List<PostSummary> getPostSummaryByTagSlug(String slug) {
        LOGGER.info("Find by tag called for slug {}", slug);

        List<PostSummary> postSummaries = postRepository.getPostSummaryByTagSlug(slug);
        postSummaries.forEach(ps -> ps.setHtmlContent(contentHelper.markdownToHtml(ps.getContent())));

        return sortedSummary(postSummaries);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post persistPost(User user, String title, String metatitle, String summary, String content) {
        Person person = personService.findByUsername(user.getUserName()).orElseThrow(() -> new RuntimeException("No user found for username : " + user.getUserName()));

        Post post = new Post(
                person,
                title,
                metatitle,
                summary,
                contentHelper.slugify(title),
                PostType.POST, content
        );
        return save(post);
    }

    public Post persistPost(User user, PostCandidate postCandidate) {
        Person person = personService.findByUsername(user.getUserName()).orElseThrow(() -> new RuntimeException("No user found for username : " + user.getUserName()));
        Post post;
        if (postCandidate.getId() == null) {
            post = new Post(
                    person,
                    postCandidate.getTitle(),
                    postCandidate.getMetaTitle(),
                    postCandidate.getSummary(),
                    contentHelper.slugify(postCandidate.getTitle()),
                    postCandidate.getPostType(),
                    postCandidate.getContent()
            );
            post.setPostStatus(PostStatus.DRAFT);
        } else {
            post = postRepository.findById(postCandidate.getId());
            updatePost(post, postCandidate);
        }

        syncCategories(post, postCandidate.getCategories());
        syncTags(post, postCandidate);
        return save(post);
    }

    private void updatePost(Post post, PostCandidate postCandidate) {
        post.setContent(postCandidate.getContent());
        post.setTitle(postCandidate.getTitle());
        post.setMetaTitle(postCandidate.getMetaTitle());
        post.setSummary(postCandidate.getSummary());
        if (postCandidate.isPublished()) {
            post.publish();
        } else {
            post.unPublish();
        }
        post.setSlug(contentHelper.slugify(postCandidate.getTitle()));
    }

    public void addPostToCategory(Post post, Category category) {
        if (!post.hasCategory(category)) {
            category.addPost(post);
        }
    }

    public void addPostToCategory(Post post, Long categoryId) {
        Category category = categoryService.findOne(categoryId);
        addPostToCategory(post, category);
    }

    public void syncCategories(Post post, Set<Long> categoryIds) {
        List<Category> toRemove = post.getCategories().stream()
                .filter(c -> !categoryIds.contains(c.getId()))
                .collect(Collectors.toList());

        for (Category category : toRemove) {
            post.removeCategory(category);
        }

        for (Long id : categoryIds) {
            addPostToCategory(post, id);
        }
    }

    private void syncTags(Post post, PostCandidate postCandidate) {

        List<Tag> tagsRemoved = post.getTags().stream()
                .filter(tag -> !postCandidate.getTags().contains(tag.getId()))
                .collect(Collectors.toList());

        for (Tag tag : tagsRemoved) {
            post.removeTag(tag);
        }

        for (Long tagId : postCandidate.getTags()) {
            Tag tag = tagService.findOne(tagId);
            if (!post.hasTag(tag)) {
                post.addTag(tag);
            }
        }
    }

    public Optional<Post> findPostBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }
}
