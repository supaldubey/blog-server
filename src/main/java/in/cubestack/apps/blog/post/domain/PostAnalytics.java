package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "postAnalytics")
@SequenceGenerator(name = "default_gen", sequenceName = "postAnalytics_id_seq", allocationSize = 1)
public class PostAnalytics extends BaseModel {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @Column
    private Long likes;

    @Column
    private Long views;

    PostAnalytics() {
    }

    public PostAnalytics(Post post) {
        this.post = post;
        this.likes = 0L;
        this.views = 0L;
    }

    public Post getPost() {
        return post;
    }

    public void liked() {
        likes++;
    }

    public void viewed() {
        views++;
    }

    public Long getLikes() {
        return likes;
    }

    public Long getViews() {
        return views;
    }
}
