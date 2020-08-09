package in.cubestack.apps.blog.post.service;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RegisterForReflection
public class PostSummary {

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
    private String tags;
    private String categories;

    private BigInteger likes;
    private BigInteger views;

    public PostSummary(Long id, String firstName, String lastName, String username, String title, String metaTitle, String summary, String slug, String postType, String postStatus, Date publishedAt, String content, String tags, String categories, BigInteger likes, BigInteger views) {
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
        this.publishedAt = DateTimeFormatter.ofPattern("dd-MMM-yy hh:mm a").format(LocalDateTime.ofInstant(publishedAt.toInstant(), ZoneId.systemDefault()));
        this.likes = likes;
        this.views = views;
        this.content = content;
        this.tags = tags;
        this.categories = categories;
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

    public String getTags() {
        return tags;
    }

    public String getCategories() {
        return categories;
    }

    public List<String> getCategoryTitles() {
        return Arrays.stream(categories.split(",")).map(String::trim).distinct().collect(Collectors.toList());
    }


    public List<String> getTagTitles() {
        return Arrays.stream(tags.split(",")).map(String::trim).distinct().collect(Collectors.toList());
    }
}
