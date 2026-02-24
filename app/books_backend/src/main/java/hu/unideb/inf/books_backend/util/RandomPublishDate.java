package hu.unideb.inf.books_backend.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class RandomPublishDate {

    public static OffsetDateTime getRandomPublishDate() {
        long start = OffsetDateTime.parse("1900-01-01T00:00:00+00:00").toInstant().toEpochMilli();
        long end = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        long diff = end - start;
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(start + (long)(diff * Math.random())), ZoneOffset.UTC);
    }

}
