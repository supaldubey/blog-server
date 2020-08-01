package in.cubestack.apps.blog.tag.repo;

import in.cubestack.apps.blog.tag.domain.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepository implements PanacheRepositoryBase<Tag, Long> {

}
