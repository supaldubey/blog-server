package in.cubestack.apps.blog.admin.resource;

import in.cubestack.apps.blog.post.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagCandidate {

    private long tagId;
    private String tagName;

    public static TagCandidate from(Tag tag) {
        return new TagCandidate(tag.getId(), tag.getTitle());
    }

    public Tag toTag() {
        return new Tag(tagId, tagName);
    }
}
