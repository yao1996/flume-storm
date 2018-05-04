package info.ykqfrost.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 * 日志信息bean
 */
public class Log {
    private static final String REGEX_LEVEL = "\\[ ([a-zA-Z])* ]";
    private static final String REGEX_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final Pattern PATTERN_DATE = Pattern.compile(REGEX_DATE);
    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    private Long logId;
    private Date date;
    private String msg;
    private String location;

    public static Log parseFromEventBody(String body) {
        Log log = new Log();
        String[] contents = body.split("-->");
        log.setMsg(contents[1].trim());
        contents[0] = contents[0].replaceFirst(REGEX_LEVEL, "");
        Matcher matcher = PATTERN_DATE.matcher(contents[0]);
        if (matcher.find()) {
            String date = matcher.group(0);
            date = date.replaceAll("-","/");
            log.setDate(new Date(date));
        } else {
            logger.debug("从event提取日期失败!");
        }
        String[] foos = contents[0].split(" ");
        String location = foos[foos.length - 1];
        log.setLocation(location);
        return log;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
