package in.cubestack.apps.blog.comment.service;

import in.cubestack.apps.blog.comment.domain.Comment;
import in.cubestack.apps.blog.comment.repo.CommentRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll().list();
    }
}
