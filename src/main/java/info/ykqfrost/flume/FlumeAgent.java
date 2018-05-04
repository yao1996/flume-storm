package info.ykqfrost.flume;

import com.google.common.collect.ImmutableMap;
import org.apache.flume.Channel;
import org.apache.flume.SinkRunner;
import org.apache.flume.SourceRunner;
import org.apache.flume.node.MaterializedConfiguration;
import org.apache.flume.node.PropertiesFileConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Yao Keqi
 * @date 2018/4/24
 */
public class FlumeAgent {
    private static final Logger logger = LoggerFactory.getLogger(FlumeAgent.class);

    private ImmutableMap<String, Channel> channels;
    private ImmutableMap<String, SourceRunner> sources;
    private ImmutableMap<String, SinkRunner> sinks;
    private File conf;
    private String agentName;

    public FlumeAgent(String configPath, String agentName) {
        this.conf = new File(configPath);
        this.agentName = agentName;
        initAgent();
    }

    private void initAgent() {
        PropertiesFileConfigurationProvider provider = new PropertiesFileConfigurationProvider(agentName, conf);
        MaterializedConfiguration configuration = provider.getConfiguration();
        channels = configuration.getChannels();
        logger.debug(agentName + ".channels : " + channels.toString());
        sources = configuration.getSourceRunners();
        logger.debug(agentName + ".sources : " + sources.toString());
        sinks = configuration.getSinkRunners();
        logger.debug(agentName + ".sinks : " + sinks.toString());
    }

    public void startAgent() {
        if (!channels.isEmpty()) {
            for (Channel channel : channels.values()) {
                channel.start();
            }
        }
        if (!sources.isEmpty()) {
            for (SourceRunner sourceRunner : sources.values()) {
                sourceRunner.start();
            }
        }
        if (!sinks.isEmpty()) {
            for (SinkRunner sinkRunner : sinks.values()) {
                sinkRunner.start();
            }
        }
    }

    public ImmutableMap<String, Channel> getChannels() {
        return channels;
    }
}
