package in.cubestack.apps.blog.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentHelper {

    private Parser parser;
    private final Slugify slugify = new Slugify();

    public String slugify(String content) {
        return slugify.slugify(content);
    }

    public String markdownToHtml(String markdown) {
        Node document = getParser().parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);

    }

    private Parser getParser() {
        if (parser == null) {
            synchronized (ContentHelper.class) {
                if (parser == null) {
                    parser = Parser.builder().build();
                }
            }
        }
        return parser;
    }
}
