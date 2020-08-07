package in.cubestack.apps.blog.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public static String toDateString(LocalDateTime dateTime) {
        return dateTime == null ? "" : dtf.format(dateTime);
    }
}
