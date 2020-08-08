package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;
import in.cubestack.apps.blog.comment.domain.Comment;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.service.PostSummary;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@SqlResultSetMapping(
        name = "PostViewMapping",
        classes = @ConstructorResult(
                targetClass = PostSummary.class,
                columns = {@ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "firstName"),
                        @ColumnResult(name = "lastName"),
                        @ColumnResult(name = "username"),
                        @ColumnResult(name = "title"),
                        @ColumnResult(name = "metaTitle"),
                        @ColumnResult(name = "summary"),
                        @ColumnResult(name = "slug"),
                        @ColumnResult(name = "postType"),
                        @ColumnResult(name = "postStatus"),
                        @ColumnResult(name = "publishedAt"),
                        @ColumnResult(name = "likes"),
                        @ColumnResult(name = "views")
                }))
@Table(name = "post")
@SequenceGenerator(name = "default_gen", sequenceName = "post_id_seq", allocationSize = 1)
public class Post extends BaseModel {

    @JoinColumn(name = "authorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Person author;

    @Column
    private String title;

    @Column
    private String metaTitle;

    @Column
    private String summary;

    @Column
    private String slug;

    @Column
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @Column(name = "publishedAt")
    private LocalDateTime publishedAt;

    @Column
    private String content;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private PostAnalytics postAnalytics;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostComment> postComments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategory> postCategories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Transient
    private String htmlContent;

    public Post() {
    }

    public Post(Person author, String title, String metaTitle, String summary, String slug, PostType postType, String content) {
        this.author = author;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.slug = slug;
        this.postType = postType;
        this.content = content;
        this.postStatus = PostStatus.DRAFT;
        this.postAnalytics = new PostAnalytics(this);
    }

    public void publish() {
        if (this.publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
        this.postStatus = PostStatus.PUBLISHED;
    }


    public Person getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public String getSummary() {
        return summary;
    }

    public String getSlug() {
        return slug;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public List<Tag> getTags() {
        return postTags.stream().map(PostTag::getTag).collect(Collectors.toList());
    }

    public void addTag(Tag tag) {
        this.postTags.add(new PostTag(this, tag));
    }

    public List<Comment> getComments() {
        return postComments.stream().map(PostComment::getComment).collect(Collectors.toList());
    }

    public void addComment(Comment comment) {
        this.postComments.add(new PostComment(this, comment));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public PostAnalytics getPostAnalytics() {
        return postAnalytics;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public List<Category> getCategories() {
        return postCategories.stream().map(PostCategory::getCategory).collect(Collectors.toList());
    }

    public boolean hasCategory(Category category) {
        return getCategories().stream().anyMatch(c -> c.equals(category));
    }

    public boolean hasTag(Tag tag) {
        return getTags().stream().anyMatch(t -> t.equals(tag));
    }

    public void removeCategory(Category category) {
        var matchedCategory = postCategories.stream().filter(pc -> pc.getCategory().equals(category)).findFirst();
        matchedCategory.ifPresent(postCategories::remove);
    }

    public void removeTag(Tag tag) {
        var matchedTag = postTags.stream().filter(pt -> pt.getTag().equals(tag)).findFirst();
        matchedTag.ifPresent(postTags::remove);
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public void unPublish() {
        postStatus = PostStatus.DRAFT;
        this.publishedAt = null;
    }
}
