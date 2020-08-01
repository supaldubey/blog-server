package in.cubestack.apps.blog.comment.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

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
    private CommentStatus status;

    @Column
    private String content;

    @Column(name = "publishedAt")
    private LocalDateTime publishedAt;
}
