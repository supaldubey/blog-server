package in.cubestack.apps.blog.admin.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.cubestack.apps.blog.core.resource.PersonCandidate;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.util.DateTimeHelper;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCandidate {

    private long postId;

    @FormParam("author")
    @NotEmpty(message = "Author must not be empty")
    private PersonCandidate author;

    @FormParam("title")
    @NotBlank(message = "Title must not be empty")
    private String title;

    @FormParam("metaTitle")
    @NotBlank(message = "Meta title must not be empty")
    private String metaTitle;

    @FormParam("summary")
    private String summary;

    @FormParam("content")
    @NotBlank(message = "Contents must not be empty")
    private String content;

    private String htmlContent;

    private String slug;

    private String publishedAt;

    private String updatedAt;

    @FormParam("categories")
    @NotEmpty(message = "Categories must not be empty")
    @Valid
    private List<CategoryCandidate> categories;

    @FormParam("tags")
    @NotEmpty(message = "Tags must not be empty")
    @Valid
    private List<TagCandidate> tags;

    public PostCandidate() {}

    public static PostCandidate from(Post post) {
        PostCandidate candidate = new PostCandidate();
        candidate.setPostId(post.getId());
        candidate.setAuthor(PersonCandidate.from(post.getAuthor()));
        candidate.setTitle(post.getTitle());
        candidate.setMetaTitle(post.getMetaTitle());
        candidate.setSummary(post.getSummary());
        candidate.setContent(post.getContent());
        candidate.setPublishedAt(DateTimeHelper.toDateString(post.getPublishedAt()));
        candidate.setUpdatedAt(DateTimeHelper.toDateString(post.getUpdatedAt()));
        candidate.setCategories(post.getPostCategories().stream().map(o -> CategoryCandidate.from(o.getCategory())).collect(Collectors.toList()));
        candidate.setTags(post.getPostTags().stream().map(o -> TagCandidate.from(o.getTag())).collect(Collectors.toList()));
        return candidate;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public PersonCandidate getAuthor() {
        return author;
    }

    public void setAuthor(PersonCandidate author) {
        this.author = author;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CategoryCandidate> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryCandidate> categories) {
        this.categories = categories;
    }

    public List<TagCandidate> getTags() {
        return tags;
    }

    public void setTags(List<TagCandidate> tags) {
        this.tags = tags;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
