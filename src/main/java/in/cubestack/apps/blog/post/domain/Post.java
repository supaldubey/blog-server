package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;
import in.cubestack.apps.blog.comment.domain.Comment;
import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.core.domain.PostStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
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
    private PostStatus postStatus;

    @Column(name = "publishedAt")
    private LocalDateTime publishedAt;

    @Column
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private List<PostComment> postComments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostTag> postTags = new ArrayList<>();

    Post() {
    }

    public Post(Person author, String title, String metaTitle, String summary, String slug, String content) {
        this.author = author;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.slug = slug;
        this.content = content;
    }

    public void publish() {
        this.postStatus = PostStatus.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
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
}
