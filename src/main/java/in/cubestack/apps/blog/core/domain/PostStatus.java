package in.cubestack.apps.blog.core.domain;

public enum PostStatus {
    PUBLISHED("Published"), DRAFT("Draft");

    private final String displayName;
    PostStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

