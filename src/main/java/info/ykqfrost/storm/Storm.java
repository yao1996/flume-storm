package info.ykqfrost.storm;

import info.ykqfrost.common.Constants;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Yao Keqi
 * @date 2018/3/14
 */
public class Storm {
    private ArrayList<String> spoutIds = new ArrayList<String>();

    private static final Logger logger = LoggerFactory.getLogger(Storm.class);

    private void startStorm() {
        TopologyBuilder builder = new TopologyBuilder();

        String spoutId = Constants.SPOUT_ID;
        builder.setSpout(spoutId, new FlumeSpout());
        spoutIds.add(spoutId);
        logger.debug("spout ID " + Constants.SPOUT_ID);

        BoltDeclarer reportBoltDeclarer = builder.setBolt(Constants.REPORT_BOLT_ID, new ReportBolt());
        for (String s : spoutIds) {
            reportBoltDeclarer.shuffleGrouping(s);
        }
        Config config = new Config();
        LocalCluster localCluster = new LocalCluster();
        //本地提交
        localCluster.submitTopology(Constants.TOPOLOGY_NAME, config, builder.createTopology());
    }

    //    public static void main(String[] args) {
//        SentenceSpout spout = new SentenceSpout();
//        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
//        ReportBolt reportBolt = new ReportBolt();
//
//        TopologyBuilder builder = new TopologyBuilder();
//        //线程数，默认1个线程
//        builder.setSpout(SENTENCE_SPOUT_ID, spout, 2);
//        // SentenceSpout --> SplitSentenceBolt
//        //注册bolt，2个线程 4个任务，
//        // shuffleGrouping方法告诉Storm要将SentenceSpout发射的tuple随机均匀的分发给SplitSentenceBolt的实例
//        builder.setBolt(SPLIT_BOLT_ID, splitSentenceBolt, 2).setNumTasks(4).shuffleGrouping(SENTENCE_SPOUT_ID);
//        builder.setBolt(REPORT_BOLT_ID, reportBolt, 4).fieldsGrouping(SPLIT_BOLT_ID, new Fields("words"));
//        Config config = new Config();
//        LocalCluster cluster = new LocalCluster();
//        //本地提交
//        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
//
//        Utils.sleep(10000);
//        cluster.killTopology(TOPOLOGY_NAME);
//        cluster.shutdown();
//}
}
