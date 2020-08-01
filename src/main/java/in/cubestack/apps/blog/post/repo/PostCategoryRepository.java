package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.category.domain.Category;
import in.cubestack.apps.blog.post.domain.PostCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PostCategoryRepository implements PanacheRepositoryBase<PostCategory, Long> {

    public Map<Category, Long> findTagPostCounts() {
        Map<Category, Long> counts = new HashMap<>();
        findAll().stream().forEach(o -> {
            counts.compute(o.getCategory(), (k, v) -> {
                if(v == null) v = 0L;
                v++;
                return v;
            });
        });
        return counts;
    }
}
