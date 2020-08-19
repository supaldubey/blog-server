package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Category;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

@RegisterForReflection
public class CategoryCandidate {

    private long id;

    @FormParam("title")
    private String title;
    private String metaTitle;
    private String slug;

    public CategoryCandidate() {
    }

    public CategoryCandidate(long id, String title, String metaTitle, String slug) {
        this.id = id;
        this.title = title;
        this.metaTitle = metaTitle;
        this.slug = slug;
    }

    public static CategoryCandidate from(Category category) {
        return new CategoryCandidate(category.getId(), category.getTitle(), category.getMetaTitle(), category.getSlug());
    }

    public Category toNewCategory() {
        return new Category(
                title,
                metaTitle,
                slug,
                null
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
