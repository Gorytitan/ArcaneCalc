package org.tama.marketskimmer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateManagement {
    public static Date subtractDaysFromToday(int daysRolledBack) {
        DateTime todaysDateTime = DateTime.now();
        return todaysDateTime.minusDays(daysRolledBack).toDate();
    }

    public static Date extractDateFromJson(String jsonDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return formatter.parseDateTime(jsonDate).toDate();
    }
}
