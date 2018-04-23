package info.ykqfrost.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/3/13
 * 订阅sentence spout发送的tuple，业务处理
 */
public class SplitSentenceBolt extends BaseRichBolt {
    private OutputCollector collector;

    /**
     * prepare()方法类似于ISpout 的open()方法。
     * 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
     * 本例子和SentenceSpout类一样,SplitSentenceBolt类不需要太多额外的初始化,
     * 所以prepare()方法只保存OutputCollector对象的引用。
     * 如果有不可序列化的未实例化内容需在该方法中实例化
     */
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        String sentence = tuple.getStringByField("sentences");
        String[] words = sentence.split(" ");
        for (String word : words) {
            this.collector.emit(new Values(word));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("words"));
    }
}
