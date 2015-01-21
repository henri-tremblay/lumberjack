package pro.tremblay.lumberjack;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Map;

public class NginxTest {

    @Test
    public void testParseLine() throws Exception {
        String line = "74.125.225.243 - - [18/Mar/2013:15:18:49 -0500] \"GET /index?key=val\" 302 197 \"http://www.amazon.com/gp/prime?ie=UTF8&*Version*=1&*entries*=0\" \"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\" \"-\" \"got: uid=C8D925AED17547510D26082802B0C116\" \"set: -\" 1363637929.657";
        Map<Nginx.Part, String> actual = Nginx.parseLine(line);
        MapEntry[] expected = new MapEntry[] {
                MapEntry.entry(Nginx.Part.original, line),
                MapEntry.entry(Nginx.Part.ip, "74.125.225.243"),
                MapEntry.entry(Nginx.Part.timestamp, "18/Mar/2013:15:18:49 -0500"),
                MapEntry.entry(Nginx.Part.request_method, "GET"),
                MapEntry.entry(Nginx.Part.request_uri, "/index?key=val"),
                MapEntry.entry(Nginx.Part.status_code, "302"),
                MapEntry.entry(Nginx.Part.response_size, "197"),
                MapEntry.entry(Nginx.Part.referrer,
                        "http://www.amazon.com/gp/prime?ie=UTF8&*Version*=1&*entries*=0")
        };
        Assertions.assertThat(actual).contains(expected);

    }

    @Test
    public void testMain() throws Exception {
        Nginx.main("test/pro.tremblay.lumberjack/nginx_sample.log");
    }
}