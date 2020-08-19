package in.cubestack.apps.blog.post.service;

import in.cubestack.apps.blog.admin.resource.CategoryCandidate;
import in.cubestack.apps.blog.post.domain.Category;
import in.cubestack.apps.blog.post.repo.CategoryRepository;
import in.cubestack.apps.blog.util.ContentHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ContentHelper contentHelper;

    public CategoryService(CategoryRepository categoryRepository, ContentHelper contentHelper) {
        this.categoryRepository = categoryRepository;
        this.contentHelper = contentHelper;
    }

    public List<CategoryCandidate> findAll() {
        return categoryRepository
                .findAll()
                .list()
                .stream()
                .sorted(Comparator.comparing(Category::getTitle))
                .map(CategoryCandidate::from).collect(Collectors.toList());
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

    public CategoryCandidate save(CategoryCandidate candidate) {
        Category category = candidate.toNewCategory();
        category.setSlug(contentHelper.slugify(candidate.getTitle()));
        categoryRepository.persist(category);
        return CategoryCandidate.from(category);
    }

    public Map<Category, Long> findCategoryPostCounts() {
        return new HashMap<>();
    }

    public CategoryCandidate findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug);
        if(category != null) return CategoryCandidate.from(category);
        return null;
    }
}
