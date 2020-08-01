package in.cubestack.apps.blog.comment.repo;

import in.cubestack.apps.blog.comment.domain.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentRepository implements PanacheRepositoryBase<Comment, Long> {
}
