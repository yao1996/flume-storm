package info.ykqfrost.mapper;

import info.ykqfrost.bean.Log;

/**
 * @author Yao Keqi
 * @date 2018/5/4
 */
public interface InfoLogMapper {

    /**
     * 插入新的info日志信息
     * @param log 日志bean
     */
    void insertInfo(Log log);
}
