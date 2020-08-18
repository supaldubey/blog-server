package in.cubestack.apps.blog.post.service;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RegisterForReflection
public class PostSummary {

    public static final int WORDS_PER_MINUTE = 200;
    public static final int AVERAGE_CHARS_PER_WORD = 6;

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String title;
    private String metaTitle;
    private String summary;
    private String slug;
    private String postType;
    private String postStatus;
    private String content;
    private String htmlContent;
    private String publishedAt;
    private List<PostMeta> tags;
    private List<PostMeta> categories;

    private BigInteger likes;
    private BigInteger views;

    private String readTime;

    public static class PostMeta {
        private String name;
        private String slug;

        public PostMeta() {
        }

        public PostMeta(String name, String slug) {
            this.name = name;
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }
    }

    public PostSummary(Long id, String firstName, String lastName, String username, String title, String metaTitle, String summary, String slug,
                       String postType, String postStatus, Date publishedAt, String content, String tags, String categories, BigInteger likes, BigInteger views) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.slug = slug;
        this.postType = postType;
        this.postStatus = postStatus;
        this.publishedAt = findTime(publishedAt);
        this.likes = likes;
        this.views = views;
        this.content = content;
        this.readTime = readTime(content);
        this.tags = toMeta(tags);
        this.categories = toMeta(categories);
    }

    private List<PostMeta> toMeta(String metas) {
        return Stream.of(metas.split(","))
                .distinct()
                .map(o1 -> o1.trim().split("\\|"))
                .map(o2 -> new PostMeta(o2[0].trim(), o2[1].trim()))
                .collect(Collectors.toList());
    }

    private String readTime(String content) {
        int words = content.length() / AVERAGE_CHARS_PER_WORD;

        return words / WORDS_PER_MINUTE + " min read";
    }

    private String findTime(Date publishedAt) {
        if (publishedAt == null) {
            return "NA";
        }
        return DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a").format(LocalDateTime.ofInstant(publishedAt.toInstant(), ZoneId.systemDefault()));
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public String getSummary() {
        return summary;
    }

    public String getSlug() {
        return slug;
    }

    public String getPostType() {
        return postType;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public BigInteger getLikes() {
        return likes;
    }

    public BigInteger getViews() {
        return views;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getContent() {
        return content;
    }

    public List<PostMeta> getTags() {
        return tags;
    }

    public List<PostMeta> getCategories() {
        return categories;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}
