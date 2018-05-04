package info.ykqfrost.storm;

import org.apache.flume.Channel;
import org.apache.flume.SinkRunner;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
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
    }

    public void nextTuple() {

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("log"));
    }
}
