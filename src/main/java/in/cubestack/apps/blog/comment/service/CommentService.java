package in.cubestack.apps.blog.comment.service;

import in.cubestack.apps.blog.comment.domain.Comment;
import in.cubestack.apps.blog.comment.repo.CommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll().list();
    }
}
