package in.cubestack.apps.blog.post.service;

import java.math.BigInteger;
import java.util.Date;

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
    private Date publishedAt;

    private BigInteger likes;
    private BigInteger views;

    public PostSummary(Long id, String firstName, String lastName, String username, String title, String metaTitle, String summary, String slug, String postType, String postStatus, Date publishedAt, BigInteger likes, BigInteger views) {
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
        this.publishedAt = publishedAt;
        this.likes = likes;
        this.views = views;
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

    public Date getPublishedAt() {
        return publishedAt;
    }

    public BigInteger getLikes() {
        return likes;
    }

    public BigInteger getViews() {
        return views;
    }
}
