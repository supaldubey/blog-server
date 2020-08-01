package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.post.domain.PostTag;
import in.cubestack.apps.blog.tag.domain.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PostTagRepository implements PanacheRepositoryBase<PostTag, Long> {

    public Map<Tag, Long> findTagPostCounts() {
        Map<Tag, Long> counts = new HashMap<>();
        findAll().stream().forEach(o -> {
            counts.compute(o.getTag(), (k, v) -> {
                if(v == null) v = 0L;
                v++;
                return v;
            });
        });
        return counts;
    }
}
