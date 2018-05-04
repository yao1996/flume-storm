package info.ykqfrost.flume;

import org.apache.flume.Context;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 */
public abstract class AbstractMysqlSink extends AbstractSink implements Configurable {
    private static final String RES_MYBATIS = "mybatis.xml";
    private static final Logger logger = LoggerFactory.getLogger(AbstractMysqlSink.class);

    SqlSession session;
    int batchSize;

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

    public void configure(Context context) {
        logger.debug("sink context : {}", context.toString());
        String batchSizeString = context.getString("batchSize");
        if (!Strings.isBlank(batchSizeString)) {
            try {
                logger.debug("get sink batchSize : {}",batchSizeString);
                this.batchSize = Integer.parseInt(batchSizeString);
            } catch (NumberFormatException e) {
                logger.warn(String.format("Unable to convert %s to integer, using default value(%d) for maxByteToDump", "batch_size", 20));
                this.batchSize = 20;
            }
        } else {
            logger.debug("未设置batchSize");
            this.batchSize = 20;
        }
    }


}
