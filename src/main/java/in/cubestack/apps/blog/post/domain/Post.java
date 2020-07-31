package in.cubestack.apps.blog.post.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends BaseModel {

}
