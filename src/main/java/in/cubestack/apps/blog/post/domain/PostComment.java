package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;
import in.cubestack.apps.blog.comment.domain.Comment;

import javax.persistence.*;

@Entity
@Table(name = "postComment")
@SequenceGenerator(name = "default_gen", sequenceName = "postComment_id_seq", allocationSize = 1)
public class PostComment extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commentId")
    private Comment comment;

    PostComment() {
    }

    public PostComment(Post post, Comment comment) {
        this.post = post;
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public Comment getComment() {
        return comment;
    }
}
