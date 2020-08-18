package in.cubestack.apps.blog.post.repo;

import in.cubestack.apps.blog.core.domain.PostStatus;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.post.service.PostSummary;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    @Inject
    EntityManager entityManager;

    public Optional<Post> findBySlug(String slug) {
        return find("slug = ?1 and postStatus = ?2", slug, PostStatus.PUBLISHED)
                .firstResultOptional();
    }

    public List<Post> findAllPublishedPostsByCategories(List<Long> categories) {
        return find("select distinct p from Post p left join p.postCategories pc where pc.category.id in ?1 and p.postStatus = ?2 order by p.publishedAt desc", categories, PostStatus.PUBLISHED).list();
    }

    public List<Post> findAllPublishedPostsByTags(List<Long> tags) {
        return find("select distinct p from Post p left join p.postTags pt where pt.tag.id in ?1 and p.postStatus = ?2  order by p.publishedAt desc", tags, PostStatus.PUBLISHED).list();
    }

    public List<PostSummary> getPostSummary() {
        Query nativeQuery = entityManager.createNativeQuery("select p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views from post p inner join person a on a.id = p.authorId inner join postAnalytics pa on pa.postId = p.id  ", "PostViewMapping");
        return nativeQuery.getResultList();
    }

    public PostSummary getPostSummary(String slug) {
        Query nativeQuery = entityManager.createNativeQuery("select p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views, string_agg(concat(t.title,'|',t.slug), ',') tags, string_agg(concat(c.title,'|',c.slug), ',') categories " +
                        "from post p inner join person a on a.id = p.authorId " +
                        "inner join postAnalytics pa on pa.postId = p.id " +
                        "inner join postCategory pc on pc.postId = p.id inner join category c on c.id = pc.categoryId " +
                        "inner join postTag pt on pt.postId = p.id inner join tag t on t.id = pt.tagId " +
                        "and p.slug = :slug " +
                        "group by p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views",
                "PostViewMapping");
        nativeQuery.setParameter("slug", slug);
        return (PostSummary) nativeQuery.getSingleResult();
    }

    public List<PostSummary> getPostSummaryByCategorySlug(String slug) {
        Query nativeQuery = entityManager.createNativeQuery("select p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views, string_agg(concat(t.title,'|',t.slug), ',') tags, string_agg(concat(c.title,'|',c.slug), ',') categories " +
                        "from post p inner join person a on a.id = p.authorId " +
                        "inner join postAnalytics pa on pa.postId = p.id " +
                        "inner join postCategory pc on pc.postId = p.id inner join category c on c.id = pc.categoryId " +
                        "inner join postTag pt on pt.postId = p.id inner join tag t on t.id = pt.tagId " +
                        "and c.slug = :slug " +
                        "group by p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views order by p.publishedAt desc",
                "PostViewMapping");
        nativeQuery.setParameter("slug", slug);
        return (List<PostSummary>) nativeQuery.getResultList();
    }

    public List<Post> findAllByPostStatus(PostStatus postStatus) {
        return find("select p from Post p where p.postStatus = ?1 order by p.updatedAt desc", postStatus).list();
    }

    public List<PostSummary> getPostSummaryByTagSlug(String slug) {
        Query nativeQuery = entityManager.createNativeQuery("select p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views, string_agg(concat(t.title,'|',t.slug), ',') tags, string_agg(concat(c.title,'|',c.slug), ',') categories " +
                        "from post p inner join person a on a.id = p.authorId " +
                        "inner join postAnalytics pa on pa.postId = p.id " +
                        "inner join postCategory pc on pc.postId = p.id inner join category c on c.id = pc.categoryId " +
                        "inner join postTag pt on pt.postId = p.id inner join tag t on t.id = pt.tagId " +
                        "and t.slug = :slug " +
                        "group by p.id, p.metaTitle, p.title, p.summary, p.slug, p.postType, p.publishedAt, p.postStatus, p.content, a.firstName, a.lastName, a.userName, pa.likes, pa.views order by p.publishedAt desc",
                "PostViewMapping");
        nativeQuery.setParameter("slug", slug);
        return (List<PostSummary>) nativeQuery.getResultList();
    }
}
