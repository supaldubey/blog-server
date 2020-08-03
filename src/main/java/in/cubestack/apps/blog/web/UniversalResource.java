package in.cubestack.apps.blog.web;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class UniversalResource {

    @GET
    @Path("/{url: .+}")
    public String page(@PathParam("url") String url) {
        System.out.println("Requested url: " + url);
        String homePage = String.format("<h1>Home page</h1><br/><h4>Requested url is: <i>%s</i></h4>", url);
        if(url != null) {
            //TODO
        }
        return homePage;
    }
}
