package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DateTime_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(String strDate) {
        return LocalDate.parse(strDate, DateTime_FORMAT);
    }

    public static String format(LocalDate date)
    {
        return date.format(DateTime_FORMAT);
    }

}
