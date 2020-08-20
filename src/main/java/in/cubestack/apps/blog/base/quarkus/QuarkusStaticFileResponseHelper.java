package in.cubestack.apps.blog.base.quarkus;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.core.HttpHeaders;

@RouteBase
@RegisterForReflection
public class QuarkusStaticFileResponseHelper {

    public static final String CACHE_CONTROL_VAL = "private, max-age=50000";

    @RouteFilter(40)
    void headerHandler(RoutingContext rc) {
        String uri = rc.request().uri();

        if (isStaticUrl(uri)) {
            // We can make it expire later so Browser doesn't call us
            rc.response().putHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_VAL);
        }
        rc.next();
    }

    //Use min files as they are external and we don't update png and Svg much
    private boolean isStaticUrl(String uri) {
        return uri.endsWith("min.css")
                || uri.endsWith("min.js")
                || uri.endsWith(".png")
                || uri.endsWith(".svg");
    }
}
