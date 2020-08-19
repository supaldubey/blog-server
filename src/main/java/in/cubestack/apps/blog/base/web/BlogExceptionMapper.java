package in.cubestack.apps.blog.base.web;

import in.cubestack.apps.blog.post.web.BlogResource;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

@Provider
@RegisterForReflection
public class BlogExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogExceptionMapper.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable throwable) {

        LOGGER.error("Error rendering " + uriInfo.toString(), throwable);

        URI blogHome = uriInfo.getBaseUriBuilder()
                .path(BlogResource.class)
                .path("/error")
                .build();

        return Response.seeOther(blogHome).build();
    }
}
