package info.ykqfrost.flume;

import org.apache.flume.sink.AbstractSink;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 */
public abstract class AbstractMysqlSink extends AbstractSink {
    private static final String RES_MYBATIS = "mybatis.xml";
    private static final Logger logger = LoggerFactory.getLogger(AbstractMysqlSink.class);

    private SqlSession session;

    @Override
    public synchronized void start() {
        super.start();

        try {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(RES_MYBATIS));
            session = factory.openSession();
        } catch (IOException e) {
            logger.debug("读取mybatis配置文件错误");
            logger.debug(e.getMessage());
        }
        logger.debug(this.getName() + " started");
    }

    @Override
    public synchronized void stop() {
        session.close();
        logger.debug(this.getName() + " closed");
    }
}
