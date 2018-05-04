package info.ykqfrost.mapper;

import info.ykqfrost.bean.Log;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 */
public interface ErrorLogMapper {

    /**
     * 插入新的error信息
     * @param log 日志bean
     */
    void insertError(Log log);

}
