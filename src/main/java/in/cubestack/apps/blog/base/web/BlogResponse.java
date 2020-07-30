package in.cubestack.apps.blog.base.web;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class BlogResponse {
    private boolean success;
    private Object data;
    private Error error;

    BlogResponse() {
    }

    public BlogResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public static BlogResponse withError(Error error) {
        BlogResponse blogResponse = new BlogResponse(false);
        blogResponse.setError(error);
        return blogResponse;
    }

    public static BlogResponse withData(Object data) {
        BlogResponse blogResponse = new BlogResponse(true);
        blogResponse.setData(data);
        return blogResponse;
    }

}
