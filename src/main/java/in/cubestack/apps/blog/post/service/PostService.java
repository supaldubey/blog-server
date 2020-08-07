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
        if(postCandidate.isPublished()) {
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
