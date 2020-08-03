package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
@SequenceGenerator(name = "default_gen", sequenceName = "tag_id_seq", allocationSize = 1)
public class Tag extends BaseModel {

    @Column
    private String title;

    @Column
    private String content;

    Tag() {
    }

    public Tag(Long id) {
        this.id = id;
    }

    public Tag(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
