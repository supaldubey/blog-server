package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Tag;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.validation.constraints.NotBlank;

public class TagCandidate {

    @FormParam("tagId")
    private long tagId;

    private String slug;

    @FormParam("tagName")
    @NotBlank(message = "Tag name must not be blank")
    private String tagName;

    public TagCandidate() {
    }

    public long getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public TagCandidate(long tagId, String slug, String tagName) {
        this.tagId = tagId;
        this.slug = slug;
        this.tagName = tagName;
    }

    public static TagCandidate from(Tag tag) {
        return new TagCandidate(tag.getId(), tag.getSlug(), tag.getTitle());
    }

    public Tag toNewTag() {
        return new Tag(tagName);
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
