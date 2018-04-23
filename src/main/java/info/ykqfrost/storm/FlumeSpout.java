package info.ykqfrost.storm;

import info.ykqfrost.common.Constants;
import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.SinkRunner;
import org.apache.flume.Transaction;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/4/10
 */
public class FlumeSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Channel channel;
    private SinkRunner sinkRunner;

    private static final Logger logger = LoggerFactory.getLogger(FlumeSpout.class);

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        String channelId = topologyContext.getThisComponentId().replace(Constants.SPOUT_ID, "");
        this.channel = Storm.getChannels().get(channelId);
        channel.start();
    }

    public void nextTuple() {
        Transaction tsx = channel.getTransaction();
        try {
            tsx.begin();
            Event event = channel.take();
            if (event == null) {
                tsx.commit();
                return;
            }
            logger.debug("header num : " + event.getHeaders().size());
            for (Map.Entry<String, String> entry : event.getHeaders().entrySet()) {
                logger.debug("event header: " + entry.getKey() + " : " + entry.getValue());
            }
            logger.debug("body : " + new String(event.getBody()));
            collector.emit(new Values(event));
            tsx.commit();
        } finally {
            tsx.close();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("log"));
    }
}
