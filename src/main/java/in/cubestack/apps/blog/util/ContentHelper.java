package in.cubestack.apps.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ContentHelper {

    private static final int MAX_LENGTH = 120;
    private List<Extension> extensions;
    private Parser parser;
    private final Slugify slugify;

    public ContentHelper(Slugify slugify) {
        this.slugify = slugify;
    }

    public String slugify(String content) {
        String consideredContent = content;

        if (content.length() > MAX_LENGTH) {
            consideredContent = content.substring(0, MAX_LENGTH);
        }

        return slugify.slugify(consideredContent);
    }

    public String markdownToHtml(String markdown) {
        Node document = getParser().parse(markdown);
        HtmlRenderer renderer = HtmlRenderer
                .builder()
                .attributeProviderFactory(ctx -> new TableAttributeProvider())
                .extensions(extensions)
                .build();
        return renderer.render(document);
    }

    private class TableAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String s, Map<String, String> attributes) {
            if (node instanceof TableBlock) {
                attributes.put("class", "table table-sm sj-table sj-markdown-table");
            }
            if(node instanceof Link) {
                // Open links on new tab
                attributes.put("target", "_blank");
            }
        }
    }

    private Parser getParser() {
        if (parser == null) {
            synchronized (ContentHelper.class) {
                if (parser == null) {
                    extensions = Arrays.asList(TablesExtension.create());
                    parser = Parser.builder().extensions(extensions).build();
                }
            }
        }
        return parser;
    }
}
