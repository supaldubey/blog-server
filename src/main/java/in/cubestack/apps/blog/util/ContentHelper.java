package in.cubestack.apps.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ContentHelper {

    private static final int MAX_LENGTH = 120;
    private HtmlRenderer renderer;
    private Parser parser;

    private final Slugify slugify;

    public ContentHelper(Slugify slugify) {
        this.slugify = slugify;
    }

    @PostConstruct
    public void initialize() {
        List<Extension> extensions = Collections.singletonList(TablesExtension.create());
        renderer = HtmlRenderer
                .builder()
                .attributeProviderFactory(ctx -> new TableAttributeProvider())
                .extensions(extensions)
                .build();

        parser = Parser.builder().extensions(extensions).build();
    }

    public String slugify(String content) {
        String consideredContent = content;

        if (content.length() > MAX_LENGTH) {
            consideredContent = content.substring(0, MAX_LENGTH);
        }

        return slugify.slugify(consideredContent);
    }

    public String markdownToHtml(String markdown) {
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    private static class TableAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String s, Map<String, String> attributes) {
            if (node instanceof TableBlock) {
                attributes.put("class", "table table-sm sj-table sj-markdown-table");
            }
            if (node instanceof Link) {
                // Open links on new tab
                attributes.put("target", "_blank");
            }
        }
    }
}
