package ca.yp.lumberjack;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

public class NginxTest {

    @Test
    public void testParseLine() throws Exception {
        String line = "74.125.225.243 - - [18/Mar/2013:15:18:49 -0500] \"GET /index?key=val\" 302 197 \"http://www.amazon.com/gp/prime?ie=UTF8&*Version*=1&*entries*=0\" \"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\" \"-\" \"got: uid=C8D925AED17547510D26082802B0C116\" \"set: -\" 1363637929.657";
        Map<Nginx.Part, String> actual = Nginx.parseLine(line);
        MapEntry[] expected = new MapEntry[] {
                entry(Nginx.Part.original, line),
                entry(Nginx.Part.ip, "74.125.225.243"),
                entry(Nginx.Part.timestamp, "18/Mar/2013:15:18:49 -0500"),
                entry(Nginx.Part.request_method, "GET"),
                entry(Nginx.Part.request_uri, "/index?key=val"),
                entry(Nginx.Part.status_code, "302"),
                entry(Nginx.Part.response_size, "197"),
                entry(Nginx.Part.referrer, "http://www.amazon.com/gp/prime?ie=UTF8&*Version*=1&*entries*=0")
        };
        assertThat(actual).contains(expected);

    }

    @Test
    public void testMain() throws Exception {
        Nginx.main("test/lumberjack/nginx_sample.log");
    }
}