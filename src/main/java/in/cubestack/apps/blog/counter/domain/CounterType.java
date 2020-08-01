package in.cubestack.apps.blog.counter.domain;

public enum CounterType {

    POST_VIEWS,
    POST_LIKES;

    public static CounterType of(String counterType) {
        for(CounterType type: values()) {
            if(type.name().equals(counterType)) return type;
        }
        throw new IllegalArgumentException("Invalid counter type " + counterType);
    }
}
