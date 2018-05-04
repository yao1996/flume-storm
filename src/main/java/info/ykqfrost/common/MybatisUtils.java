package info.ykqfrost.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yao Keqi
 * @date 2018/5/4
 */
public class MybatisUtils {
    private static SqlSession session;
    private static final Logger logger = LoggerFactory.getLogger(MybatisUtils.class);

    static {
        try {
            InputStream in = Resources.getResourceAsStream(Constants.RES_MYBATIS);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            session = factory.openSession();
        } catch (IOException e) {
            logger.debug("读取mybatis配置文件错误");
            logger.debug(e.getMessage());
        }
    }

    public static SqlSession getSession() {
        return session;
    }
}
