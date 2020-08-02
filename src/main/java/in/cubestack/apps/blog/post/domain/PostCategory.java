package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "postCategory")
@SequenceGenerator(name = "default_gen", sequenceName = "postCategory_id_seq", allocationSize = 1)
public class PostCategory extends BaseModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    PostCategory() {}

    public PostCategory(Category category) {
        this.category = category;
    }

    public PostCategory(Category category, Post post) {
        this.category = category;
        this.post = post;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Post getPost() {
        return post;
    }

    public Category getCategory() {
        return category;
    }
}
