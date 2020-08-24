package in.cubestack.apps.blog.post.web;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class BlogContent {

    public enum ContentType {
        POST, CATEGORY, TAG
    }

    private ContentType type;
    private Object content;

    public BlogContent() {}

    public BlogContent(ContentType type, Object content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlogContent{" +
                "type=" + type +
                ", content=" + content +
                '}';
    }
}
