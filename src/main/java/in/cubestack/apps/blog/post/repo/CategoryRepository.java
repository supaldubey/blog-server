package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.post.domain.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category, Long> {
}
