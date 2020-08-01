package in.cubestack.apps.blog.category.repo;

import in.cubestack.apps.blog.category.domain.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category, Long> {
}
