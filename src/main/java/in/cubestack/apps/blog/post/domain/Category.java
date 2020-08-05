package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "category")
@SequenceGenerator(name = "default_gen", sequenceName = "category_id_seq", allocationSize = 1)
public class Category extends BaseModel {

    @Column
    private String title;

    @Column
    private String metaTitle;

    @Column
    private String slug;

    @Column
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<PostCategory> postCategories = new ArrayList<>();

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }


    public Category(Long id, String title, String metaTitle, String slug, String content) {
        this.id = id;
        this.title = title;
        this.metaTitle = metaTitle;
        this.slug = slug;
        this.content = content;
    }

    public Category(String title, String metaTitle, String slug, String content) {
        this(null, title, metaTitle, slug, content);
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Post> getPosts() {
        return postCategories.stream().map(PostCategory::getPost).collect(Collectors.toList());
    }

    public void addPost(Post post) {
        PostCategory postCategory = new PostCategory(this, post);
        postCategories.add(postCategory);
    }
}
