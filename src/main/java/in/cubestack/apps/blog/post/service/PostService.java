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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    @Inject
    PersonService personService;

    @Inject
    CategoryService categoryService;

    @Inject
    TagService tagService;

    @Inject
    ContentHelper contentHelper;

    public Optional<Post> findById(Long id) {
        return postRepository.findByIdOptional(id);
    }

    public PostCandidate findBySlug(String slug) {
        Optional<Post> postOpt = postRepository.findBySlug(slug);
        Post post = postOpt.orElseThrow(() -> new RuntimeException("Post not found for the slug " + slug));
        PostCandidate candidate = PostCandidate.from(post);
        candidate.setHtmlContent(contentHelper.markdownToHtml(candidate.getContent()));
        return candidate;
    }

    public List<PostCandidate> findAll() {
        return toCandidates(postRepository.findAll().list());
    }

    public List<PostCandidate> findAllPublishedPostsByCategories(List<Long> categories) {
        return toCandidates(postRepository.findAllPublishedPostsByCategories(categories));
    }

    public List<PostCandidate> findAllPublishedPostsByTags(List<Long> tags) {
        return toCandidates(postRepository.findAllPublishedPostsByTags(tags));
    }

    public List<PostCandidate> findAllPublished() {
        return toCandidates(postRepository.findAllByPostStatus(PostStatus.PUBLISHED));
    }

    public List<PostSummary> getAllPostSummaries() {
        return postRepository.getAllPostSummaries();
    }

    private List<PostCandidate> toCandidates(List<Post> posts) {
        return posts.stream().map(o -> {
            PostCandidate candidate = PostCandidate.from(o);
            candidate.setHtmlContent(contentHelper.markdownToHtml(candidate.getContent()));
            return candidate;
        }).collect(Collectors.toList());
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

    public PostSummary getSummary(String slug) {
        PostSummary postSummary = postRepository.getAllPostSummaries(slug);
        postSummary.setHtmlContent(contentHelper.markdownToHtml(postSummary.getContent()));
        return postSummary;
    }

    public List<PostSummary> getPostSummaryByCategorySlug(String slug) {
        List<PostSummary> postSummaries = postRepository.getPostSummaryByCategorySlug(slug);
        postSummaries.forEach(ps -> ps.setHtmlContent(contentHelper.markdownToHtml(ps.getContent())));
        return postSummaries;
    }

    public List<PostSummary> getPostSummaryByTagSlug(String slug) {
        List<PostSummary> postSummaries = postRepository.getPostSummaryByTagSlug(slug);
        postSummaries.forEach(ps -> ps.setHtmlContent(contentHelper.markdownToHtml(ps.getContent())));
        return postSummaries;
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
                .filter(t -> !postCandidate.getTags().contains(t.getId()))
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
}
