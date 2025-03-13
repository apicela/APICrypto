package com.apicela.apicrypto.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {
    public static LocalDateTime parseDate(String dateStr) {
        // Parse the date string as an Instant first (handles 'Z' suffix)
        Instant instant = Instant.parse(dateStr);
        // Convert the Instant to LocalDateTime in UTC
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
