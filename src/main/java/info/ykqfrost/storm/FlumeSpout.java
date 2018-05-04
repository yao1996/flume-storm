package info.ykqfrost.storm;

import info.ykqfrost.Main;
import info.ykqfrost.common.Constants;
import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.Transaction;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/4/10
 * spout直接从channel获取event
 */
public class FlumeSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Channel channel;

    private static final Logger logger = LoggerFactory.getLogger(FlumeSpout.class);

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        int i = Integer.parseInt(topologyContext.getThisComponentId().replace(Constants.SPOUT_ID, ""));
        this.channel = Main.channels.get(i);
    }

    public void nextTuple() {
        Transaction transaction = channel.getTransaction();
        List<Event> events = new ArrayList<Event>();
        try {
            transaction.begin();
            int batchSize = 20;
            for (int i = 0; i < batchSize; i++) {
                Event event = channel.take();
                if (event != null) {
                    events.add(event);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("error : " + e.getMessage());
        } finally {
            transaction.close();
        }
        if (events.size() != 0) {
            logger.debug("spout get event num : " + events.size());
            this.collector.emit(new Values(events));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("log"));
    }
}
