package info.ykqfrost.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/3/13
 * 发送器，发送tuple数据流
 */
public class SentenceSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private int index = 0;
    private String[] sentences = {
            "my name is soul",
            "im a boy",
            "i have a dog",
            "my dog has fleas",
            "my girl friend is beautiful"
    };

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    /**
     * 核心，向输出的collector发送数据
     */
    public void nextTuple() {
        this.collector.emit(new Values(sentences[index]));
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        Utils.sleep(1);
    }

    /**
     * 声明发送的内容
     */
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentences"));
    }
}
