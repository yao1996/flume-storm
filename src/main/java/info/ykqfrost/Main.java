package info.ykqfrost;

import com.google.common.collect.ImmutableMap;
import info.ykqfrost.common.Constants;
import info.ykqfrost.flume.FlumeAgent;
import org.apache.flume.Channel;

/**
 * @author Yao Keqi
 * @date 2018/5/2
 * 入口
 */
public class Main {
    private ImmutableMap<String, Channel> channels;

    public static void main(String[] args) {
        FlumeAgent agent = new FlumeAgent(Constants.CONFIG_PATH_1, Constants.FLUME_AGENT_NAME_1);
        agent.startAgent();
    }

}
