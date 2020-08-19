package in.cubestack.apps.blog.base.web;

import in.cubestack.apps.blog.post.web.BlogResource;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

@Provider
@RegisterForReflection
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException notFoundException) {

        URI blogHome = uriInfo.getBaseUriBuilder()
                .path(BlogResource.class)
                .path("/notFound")
                .build();

        return Response.seeOther(blogHome).build();
    }
}
