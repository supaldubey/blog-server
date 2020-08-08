package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostType;
import in.cubestack.apps.blog.post.domain.Tag;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RegisterForReflection
public class PostCandidate {

    @FormParam("id")
    private Long id;

    @FormParam("title")
    private String title;

    @FormParam("metaTitle")
    private String metaTitle;

    @FormParam("summary")
    private String summary;

    @FormParam("postType")
    private PostType postType;

    @FormParam("status")
    private PostStatus status;

    @FormParam("content")
    private String content;

    @FormParam("categories")
    private Set<Long> categories = new HashSet<>();

    @FormParam("tags")
    private Set<Long> tags = new HashSet<>();

    private String slug;

    private String htmlContent;

    private String postStatus;

    private boolean seriesOrGuide;

    private PersonCandidate person;
    private final List<TagCandidate> tagCandidates = new ArrayList<>();
    private final List<CategoryCandidate> categoryCandidates = new ArrayList<>();


    public PostCandidate(long id, String title, String metaTitle, String summary, PostType postType, String content, PostStatus postStatus) {
        this.id = id;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.postType = postType;
        this.content = content;
        this.status = postStatus;
    }

    public PostCandidate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Long> getCategories() {
        return categories;
    }

    public void setCategories(Set<Long> categories) {
        this.categories = categories;
    }

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
    }

    public List<TagCandidate> getTagCandidates() {
        return tagCandidates;
    }

    public List<CategoryCandidate> getCategoryCandidates() {
        return categoryCandidates;
    }

    private void addTagCandidate(Tag tag) {
        tagCandidates.add(new TagCandidate(tag.getId(), tag.getSlug(), tag.getTitle()));
        tags.add(tag.getId());
    }

    private void addCategoryCandidate(Category category) {
        categoryCandidates.add(new CategoryCandidate(category.getId(), category.getTitle(), category.getMetaTitle(), category.getSlug()));
        categories.add(category.getId());
    }

    public boolean hasCategory(CategoryCandidate categoryCandidate) {
        return categoryCandidates.stream().anyMatch(c -> c.getId() == categoryCandidate.getId());
    }

    public boolean hasTag(TagCandidate tagCandidate) {
        return tagCandidates.stream().anyMatch(t -> t.getTagId() == tagCandidate.getTagId());
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public boolean isSeriesOrGuide() {
        return seriesOrGuide;
    }

    public void setSeriesOrGuide(boolean seriesOrGuide) {
        this.seriesOrGuide = seriesOrGuide;
    }

    public static PostCandidate from(Post post) {
        PostCandidate postCandidate = new PostCandidate(
                post.getId(),
                post.getTitle(),
                post.getMetaTitle(),
                post.getSummary(),
                post.getPostType(),
                post.getContent(),
                post.getPostStatus()
        );

        postCandidate.setSlug(post.getSlug());
        postCandidate.setPostStatus(post.getPostStatus().name());
        postCandidate.setSeriesOrGuide(!post.getPostType().equals(PostType.POST));

        postCandidate.person = PersonCandidate.from(post.getAuthor());
        for (Tag tag : post.getTags()) {
            postCandidate.addTagCandidate(tag);
        }

        for (Category category : post.getCategories()) {
            postCandidate.addCategoryCandidate(category);
        }

        return postCandidate;
    }

    public PersonCandidate getPerson() {
        return person;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public boolean isPublished() {
        return PostStatus.PUBLISHED == status;
    }
}