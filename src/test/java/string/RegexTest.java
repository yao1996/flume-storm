package string;

import org.junit.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yao Keqi
 * @date 2018/5/4
 */
public class RegexTest {
    private static final String REGEX_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    @Test
    public void testDate() {
        String date = "[ ERROR ] 2018-05-04 04:51:00 info.ykqfrost.Main.main(Main.java:18) --> error sink 1";
        Pattern pattern = Pattern.compile(REGEX_DATE);
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group());
        }
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testNewDate() {
        String s = "2018-05-04 04:51:00";
        Date date = new Date(s);
        System.out.println(date.getTime());
    }
}
