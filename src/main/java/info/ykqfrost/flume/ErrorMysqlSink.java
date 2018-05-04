package info.ykqfrost.flume;

import info.ykqfrost.bean.Log;
import info.ykqfrost.mapper.ErrorLogMapper;
import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yao Keqi
 * @date 2018/5/2
 */
public class ErrorMysqlSink extends AbstractMysqlSink {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogMapper.class);

    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = this.getChannel();
        Transaction transaction = channel.getTransaction();
        List<Event> events = new ArrayList<Event>(batchSize);
        try {
            transaction.begin();
            for (int i = 0; i < batchSize; i++) {
                Event event = channel.take();
                if (event != null) {
                    logger.debug("event content : {}", event.toString());
                    events.add(event);
                }
            }
            if (events.size() == 0) {
                result = Status.BACKOFF;
            } else {
                logger.debug("sink get event num : " + events.size());
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.debug("sink 读取event错误");
            logger.debug(e.getMessage());
        } finally {
            transaction.close();
        }
        if (events.size() != 0) {
            ErrorLogMapper mapper = session.getMapper(ErrorLogMapper.class);
            for (Event event : events) {
                String body = new String(event.getBody());
                Log log = Log.parseFromEventBody(body);
                mapper.insertError(log);
            }
            session.commit();
        }
        return result;
    }
}
