package in.cubestack.apps.blog.post.domain;

public enum PostType {
    POST("Post"), COURSE("Course"), SERIES("Series");


    private final String displayName;
    PostType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
