package in.cubestack.apps.blog.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentHelper {

    private static final int MAX_LENGTH = 30;

    private Parser parser;
    private final Slugify slugify = new Slugify();

    public String slugify(String content) {
        String consideredContent = content;
        if (content.length() > MAX_LENGTH) {
            consideredContent = content.substring(0, MAX_LENGTH);
        }
        return slugify.slugify(consideredContent);
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
