package in.cubestack.apps.blog.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContentHelperTest {

    private ContentHelper contentHelper;

    @BeforeEach
    public void init() {
        contentHelper = new ContentHelper();
    }

    @Test
    public void testMdConvertor() {
        String html = contentHelper.markdownToHtml("# Hi \n This is test");
        assertEquals(html, "<h1>Hi</h1>\n<p>This is test</p>\n");
    }

    @Test
    public void testMdConvertorWithLinks() {
        String html = contentHelper.markdownToHtml("# Hi \n This is test\nclick [here](http://cubestack.in)");
        assertEquals(html, "<h1>Hi</h1>\n<p>This is test\nclick <a href=\"http://cubestack.in\">here</a></p>\n");
    }

    @Test
    public void testSlug() {
        String output = contentHelper.slugify("This is my infy");
        assertEquals(output, "this-is-my-infy");
    }

    @Test
    public void testSlugLongStringContent() {
        String output = contentHelper.slugify("This is my infy This is my infy This is my infy This is my infy This is my infy This is my infy This is my infy This is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infyThis is my infy");
        assertEquals(output, "this-is-my-infy-this-is-my-inf");
    }
}