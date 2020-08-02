package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.post.domain.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepository implements PanacheRepositoryBase<Tag, Long> {

}
