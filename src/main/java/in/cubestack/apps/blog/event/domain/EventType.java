package in.cubestack.apps.blog.event.domain;

public enum EventType {

    POST_VIEWS,
    POST_LIKES;

    public static EventType of(String counterType) {
        for (EventType type : values()) {
            if (type.name().equals(counterType)) return type;
        }
        throw new IllegalArgumentException("Invalid counter type " + counterType);
    }
}
