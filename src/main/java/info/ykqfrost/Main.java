package info.ykqfrost;

import info.ykqfrost.common.Constants;
import info.ykqfrost.flume.FlumeAgent;
import info.ykqfrost.storm.Storm;
import org.apache.flume.Channel;

import java.util.ArrayList;

/**
 * @author Yao Keqi
 * @date 2018/5/2
 * 入口
 */
public class Main {
    public static ArrayList<Channel> channels = new ArrayList<Channel>();

    public static void main(String[] args) {
        FlumeAgent agent1 = new FlumeAgent(Constants.CONFIG_PATH_1, Constants.FLUME_AGENT_NAME_1);
        Channel channel = agent1.getFileNormalChannel();
        if (channel != null) {
            channels.add(channel);
        }
        FlumeAgent agent2 = new FlumeAgent(Constants.CONFIG_PATH_2,Constants.FLUME_AGENT_NAME_2);
        channel = agent2.getFileNormalChannel();
        if (channel != null) {
            channels.add(channel);
        }
        Storm storm = new Storm(channels);
        agent1.startAgent();
        agent2.startAgent();
        storm.startStorm();
    }

}
