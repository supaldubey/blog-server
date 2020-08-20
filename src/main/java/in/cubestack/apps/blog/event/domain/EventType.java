package in.cubestack.apps.blog.event.domain;

public enum EventType {

    TAG_CREATED,
    POST_CREATED,
    CATEGORY_CREATED,
    POST_UPDATED,
    TAG_UPDATED,
    CATEGORY_UPDATED,
    POST_VIEWS,
    POST_LIKES;

    public static EventType of(String counterType) {
        for (EventType type : values()) {
            if (type.name().equals(counterType)) return type;
        }
        throw new IllegalArgumentException("Invalid counter type " + counterType);
    }
}
