package in.cubestack.apps.blog.category.service;

import in.cubestack.apps.blog.category.domain.Category;
import in.cubestack.apps.blog.category.repo.CategoryRepository;
import in.cubestack.apps.blog.post.repo.PostCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ApplicationScoped
public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private PostCategoryRepository postCategoryRepository;

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

    private Supplier<UnsupportedOperationException> unsupportedOpsSupplier() {
        return () -> new UnsupportedOperationException("Not yet implemented");
    }

    public Map<Category, Long> findCategoryPostCounts() {
        return postCategoryRepository.findTagPostCounts();
    }
}
