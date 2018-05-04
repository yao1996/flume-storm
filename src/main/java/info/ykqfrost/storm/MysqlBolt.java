package info.ykqfrost.storm;

import info.ykqfrost.bean.Log;
import info.ykqfrost.common.MybatisUtils;
import info.ykqfrost.mapper.InfoLogMapper;
import org.apache.flume.Event;
import org.apache.ibatis.session.SqlSession;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/5/4
 * 将spout的日志上传数据库
 */
public class MysqlBolt extends BaseRichBolt {
    private SqlSession session;
    private InfoLogMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(MysqlBolt.class);

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        session = MybatisUtils.getSession();
        mapper = session.getMapper(InfoLogMapper.class);
    }

    public void execute(Tuple tuple) {
        List<Event> events = (List<Event>) tuple.getValueByField("log");
        logger.debug("mysqlBolt get events num : " + events.size());
        for (Event event : events) {
            String body = new String(event.getBody());
            Log log = Log.parseFromEventBody(body);
            mapper.insertInfo(log);
        }
        session.commit();
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
