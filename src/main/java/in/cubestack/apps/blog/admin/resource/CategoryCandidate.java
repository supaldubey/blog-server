package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCandidate {

    private long id;
    private String title;
    private String metaTitle;
    private String slug;

    public static CategoryCandidate from(Category category) {
        return CategoryCandidate.builder()
                .id(category.getId())
                .title(category.getTitle())
                .metaTitle(category.getMetaTitle())
                .slug(category.getSlug())
                .build();
    }

    public Category toCategory() {
        return new Category(
                id,
                title,
                metaTitle,
                slug,
                null
        );
    }
}
