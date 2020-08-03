package in.cubestack.apps.blog.comment.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;
import in.cubestack.apps.blog.core.domain.Person;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@SequenceGenerator(name = "default_gen", sequenceName = "comment_id_seq", allocationSize = 1)
public class Comment extends BaseModel {

    @Column
    private String title;

    @Column
    private String commenterId;

    @Column
    @Enumerated(EnumType.STRING)
    private CommentStatus status = CommentStatus.PUBLISHED;

    @Column
    private String content;

    @Column(name = "publishedAt")
    private LocalDateTime publishedAt;

    Comment() {
    }

    public Comment(String title, CommentStatus status, String content, LocalDateTime publishedAt) {
        this.title = title;
        this.status = status;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    public void commentBy(Person person) {
        this.commenterId = String.valueOf(person.getId());
    }

    public String getTitle() {
        return title;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}
