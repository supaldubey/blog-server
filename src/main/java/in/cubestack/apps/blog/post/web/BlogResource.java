package in.cubestack.apps.blog.post.web;

import in.cubestack.apps.blog.core.domain.Person;
import in.cubestack.apps.blog.post.domain.Post;
import in.cubestack.apps.blog.util.BlogUtil;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("blog")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance post(Post post);
        public static native TemplateInstance postView1(Post post);
        public static native TemplateInstance postView2(Post post);
    }

    @GET
    @Path("posts/view1")
    public TemplateInstance postView1() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\bitsevn\\work\\source\\blog-server\\src\\main\\resources\\sample")));
        Post post = new Post(
                new Person("Arun", "Kumar", "bitsevn"),
                "REST APIs with Quarkus RestEasy",
                "Getting started guide",
                null,
                "rest-apis-with-quarkus-resteasy",
                BlogUtil.markdownToHtmlFn().apply(content)
        );
        return Templates.postView1(post);
    }

    @GET
    @Path("posts/view2")
    public TemplateInstance postView2() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\bitsevn\\work\\source\\blog-server\\src\\main\\resources\\sample")));
        Post post = new Post(
                new Person("Arun", "Kumar", "bitsevn"),
                "REST APIs with Quarkus RestEasy",
                "Getting started guide",
                null,
                "rest-apis-with-quarkus-resteasy",
                BlogUtil.markdownToHtmlFn().apply(content)
        );
        return Templates.postView2(post);
    }
}
