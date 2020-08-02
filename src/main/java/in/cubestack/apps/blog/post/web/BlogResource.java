package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.util.ContentHelper;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("blog")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    @Inject
    ContentHelper contentHelper;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance post(Post post);

        public static native TemplateInstance postView1(Post post);

        public static native TemplateInstance postView2(Post post);
    }

    @GET
    @Path("posts/view1")
    public TemplateInstance postView1() throws IOException, URISyntaxException {
        String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("/sample").toURI())));
        Post post = new Post(
                new Person("Arun", "Kumar", "bitsevn"),
                "REST APIs with Quarkus RestEasy",
                "Getting started guide",
                null,
                "rest-apis-with-quarkus-resteasy",
                contentHelper.markdownToHtml(content)
        );
        return Templates.postView1(post);
    }

    @GET
    @Path("posts/view2")
    public TemplateInstance postView2() throws IOException, URISyntaxException {
        String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("/sample").toURI())));
        Post post = new Post(
                new Person("Arun", "Kumar", "bitsevn"),
                "REST APIs with Quarkus RestEasy",
                "Getting started guide",
                null,
                "rest-apis-with-quarkus-resteasy",
                contentHelper.markdownToHtml(content)
        );
        return Templates.postView2(post);
    }
}
