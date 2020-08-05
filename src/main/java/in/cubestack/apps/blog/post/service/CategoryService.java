package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.repo.CategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll().list();
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category update(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    public Category save(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    public Map<Category, Long> findCategoryPostCounts() {
        return new HashMap<>();
    }

}
