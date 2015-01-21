package pro.tremblay.lumberjack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by htrembl2 on 1/20/15.
 */
public class Nginx {

    private static final Pattern logLinePattern =
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})? - - \\[(.*)\\] \\\"(\\w+) ([^\"]*)\\\" (\\d{3}) (\\d+) \\\"([^\"]*)\\\".*");

    public enum Part {
        original,
        ip,
        timestamp,
        request_method,
        request_uri,
        status_code,
        response_size,
        referrer
    }

    public static Map<Part, String> parseLine(String line) {
        Matcher matcher = logLinePattern.matcher(line);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Invalid log line " + line);
        }
        int count = matcher.groupCount();
        Map<Part, String> result = new HashMap<>(count);
        Part[] values = Part.values();
        for (int i = 0; i <= count; i++) {
            result.put(values[i], matcher.group(i));
        }
        return result;
    }

    public static Stream<String> readFile(String filename) {
        try {
            return Files.lines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... filenames) throws Exception {
        List<Map<Part, String>> result = Stream.of(filenames)
                .map(Nginx::readFile)
                .flatMap(Function.identity())
                .map(Nginx::parseLine)
                .collect(Collectors.toList());
        System.out.println(result);
    }
}
