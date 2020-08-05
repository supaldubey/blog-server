package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Tag;

public class TagCandidate {

    private long tagId;
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
