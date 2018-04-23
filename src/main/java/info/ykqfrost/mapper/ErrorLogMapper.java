package info.ykqfrost.mapper;

import info.ykqfrost.bean.ErrorLog;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 */
public interface ErrorLogMapper {

    /**
     * 插入新的error信息
     */
    void insertError(ErrorLog errorLog);

}
