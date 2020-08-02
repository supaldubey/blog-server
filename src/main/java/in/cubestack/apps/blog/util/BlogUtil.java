package in.cubestack.apps.blog.util;

import com.github.slugify.Slugify;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.function.Function;

public class BlogUtil {

    private static final Slugify SLUGIFY = new Slugify();

    public static Function<String, String> slugifyFn() {
        return (text) -> SLUGIFY.slugify(text);
    }

    public static Function<String, String> markdownToHtmlFn() {
        return (markdown) -> {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdown);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            return renderer.render(document);
        };
    }
}
