package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.domain.PostType;
import in.cubestack.apps.blog.post.domain.Tag;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RegisterForReflection
public class PostCandidate {

    private long id;

    @FormParam("title")
    private String title;

    @FormParam("metatitle")
    private String metaTitle;

    @FormParam("summary")
    private String summary;

    @FormParam("postType")
    private PostType postType;

    @FormParam("content")
    private String content;

    @FormParam("categories")
    private Set<Long> categories;

    @FormParam("tags")
    private Set<Long> tags;

    private List<TagCandidate> tagCandidates = new ArrayList<>();
    private List<CategoryCandidate> categoryCandidates = new ArrayList<>();

    public PostCandidate() {
    }

    public PostCandidate(long id, String title, String metaTitle, String summary, PostType postType, String content) {
        this.id = id;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.postType = postType;
        this.content = content;
    }

    public long getId() {
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
        tagCandidates.add(new TagCandidate(tag.getId(), tag.getTitle()));
    }

    private void addCategoryCandidate(Category category) {
        categoryCandidates.add(new CategoryCandidate(category.getId(), category.getTitle(), category.getMetaTitle(), category.getSlug()));
    }

    public static PostCandidate from(Post post) {
        PostCandidate postCandidate = new PostCandidate(
                post.getId(),
                post.getTitle(),
                post.getMetaTitle(),
                post.getSummary(),
                post.getPostType(),
                post.getContent()
        );

        for (Tag tag : post.getTags()) {
            postCandidate.addTagCandidate(tag);
        }

        for (Category category : post.getCategories()) {
            postCandidate.addCategoryCandidate(category);
        }

        return postCandidate;
    }
}
