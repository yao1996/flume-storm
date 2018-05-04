package info.ykqfrost.common;

/**
 * @author Yao Keqi
 * @date 2018/3/23
 */
public class Constants {
    public static final String CONFIG_PATH_1 = "src/main/resources/agent1.conf";
    public static final String CONFIG_PATH_2 = "src/main/resources/agent2.conf";
    public static final String FLUME_AGENT_NAME_1 = "agent1";
    public static final String FLUME_AGENT_NAME_2 = "agent2";
    public static final String FLUME_CHANNEL_NAME_1 = "fileErrorChannel";
    public static final String FLUME_CHANNEL_NAME_2 = "fileNormalChannel";

    public static final int DEFAULT_BATCH_SIZE = 20;

    public static final String SPOUT_ID = "spout_id_";
    public static final String REPORT_BOLT_ID = "report_bolt_id_";
    public static final String TOPOLOGY_NAME = "flume_storm_topology";
}
