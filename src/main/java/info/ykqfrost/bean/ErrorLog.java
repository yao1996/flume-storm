package info.ykqfrost.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 * 错误日志信息
 */
public class ErrorLog {
    private static final String REGEX_LEVEL = "\\[ ([a-zA-Z])* ]";
    private static final String REGEX_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final Pattern PATTERN_DATE = Pattern.compile(REGEX_DATE);
    private static final Logger logger = LoggerFactory.getLogger(ErrorLog.class);

    private Long errorId;
    private Date date;
    private String msg;
    private String location;

    public static ErrorLog parseFromEventBody(String body) {
        ErrorLog errorLog = new ErrorLog();
        String[] contents = body.split("-->");
        errorLog.setMsg(contents[1].trim());
        contents[0] = contents[0].replaceFirst(REGEX_LEVEL, "");
        Matcher matcher = PATTERN_DATE.matcher(contents[0]);
        if (matcher.matches()) {
            String date = matcher.group(0);
            errorLog.setDate(new Date(date));
        } else {
            logger.debug("从event提取日期失败!");
        }
        String[] foos = contents[0].split(" ");
        String location = foos[foos.length - 1];
        errorLog.setLocation(location);
        return errorLog;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
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
