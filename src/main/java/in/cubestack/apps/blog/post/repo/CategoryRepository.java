package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.post.domain.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category, Long> {

    public Category findBySlug(String slug) {
        List<Category> categoryList = find("slug", slug).list();
        return categoryList != null && categoryList.size() > 0 ? categoryList.get(0) : null;
    }

}
