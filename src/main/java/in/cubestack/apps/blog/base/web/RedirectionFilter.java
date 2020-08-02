package in.cubestack.apps.blog.base.web;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RedirectionFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        int status = responseContext.getStatus();
        if (status == 401 || status == 403) {
            responseContext.setStatus(301);
            responseContext.getHeaders().putSingle("location", "/admin/login");
        }
    }
}