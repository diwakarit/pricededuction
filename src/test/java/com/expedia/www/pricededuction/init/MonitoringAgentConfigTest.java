package com.expedia.www.pricededuction.init;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MonitoringAgentConfigTest {
    @Test
    public void testMonitoringAgentContainsExpectedMap() {
        final MonitoringAgentConfig config = new MonitoringAgentConfig();
        config.setHostName("www.coolhostname.com", "1");
        final Map<String, String> monitoringAgent = config.getMonitoringAgent();

        assertTrue(monitoringAgent.containsKey("application.hostname"));
        assertTrue(monitoringAgent.containsValue("1-www.coolhostname.com"));
    }

    @Test
    public void testMonitoringAgentContainsExpectedMapForLongVersionName() {
        final MonitoringAgentConfig config = new MonitoringAgentConfig();
        config.setHostName("www.coolhostname.com", "1thisIsALongVersion");
        final Map<String, String> monitoringAgent = config.getMonitoringAgent();

        assertTrue(monitoringAgent.containsValue("1thisI-www.coolhostname.com"));
    }
}
