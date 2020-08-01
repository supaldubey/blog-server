package in.cubestack.apps.blog.tag.repo;

import in.cubestack.apps.blog.tag.domain.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class TagRepository implements PanacheRepositoryBase<Tag, Long> {

    public Map<Tag, Long> findTagPostCounts() {
        //find("select t, count(pt.id) as from Tag t left join PostTag pt on t.id=pt.tagId left join Post p on t.postId=p.id and p.postStatus = ?1", PostStatus.PUBLISHED).list();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
