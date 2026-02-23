package aiss.gitlabminer.util;

import org.springframework.http.HttpHeaders;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Util {

    public static String getNextPageUrl(HttpHeaders headers) {
        String result = null;

        List<String> linkHeader = headers.get("Link");
        if (linkHeader == null)
            return null;

        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        for (String token : links.split(", ")) {
            if (token.endsWith("rel=\"next\"")) {
                int idx = token.indexOf('>');
                result = token.substring(1, idx);
                break;
            }
        }
        return result;
    }

    public static LocalDateTime StringToLocalDateTime(String dateToParse) {

        Instant instant = Instant.parse(dateToParse);
        LocalDateTime parsedDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        return parsedDate;
    }
}
