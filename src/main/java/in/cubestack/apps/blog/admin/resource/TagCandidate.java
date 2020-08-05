package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Tag;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

import javax.validation.constraints.NotBlank;

public class TagCandidate {

    @FormParam("tagId")
    private long tagId;

    @FormParam("tagName")
    @NotBlank(message = "Tag name must not be blank")
    private String tagName;

    public TagCandidate() {}

    public TagCandidate(long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public static TagCandidate from(Tag tag) {
        return new TagCandidate(tag.getId(), tag.getTitle());
    }

    public Tag toTag() {
        return new Tag(tagId, tagName);
    }
}
