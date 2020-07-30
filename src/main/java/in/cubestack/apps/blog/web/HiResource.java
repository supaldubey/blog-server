package in.cubestack.apps.blog.web;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hi")
public class HiResource {

    @GET
    @Path("hi")
    @PermitAll
    public String hi() {
        return "Hi";
    }

    @GET
    @Path("hello")
    @RolesAllowed("hello")
    public String ok() {
        return "not ok";
    }

    @GET
    @Path("waao")
    @RolesAllowed("Waao")
    public String log() {
        return "not ok";
    }
}
