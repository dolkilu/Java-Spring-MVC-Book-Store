package hkmu.comps380f.s1326557_project.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

        public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
            // yyyy-MM-dd HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return dateTime.format(formatter);
        }

}
