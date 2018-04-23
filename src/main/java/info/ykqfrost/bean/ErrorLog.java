package info.ykqfrost.bean;

import java.util.Date;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 * 错误日志信息
 */
public class ErrorLog {
    private Long errorId;
    private Date date;
    private String msg;
    private String location;

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
