package com.expedia.www.pricededuction.init;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableMetrics
@RequiredArgsConstructor
public class MetricsConfiguration extends MetricsConfigurerAdapter implements ServletContextInitializer {

  private final MetricRegistry metricRegistry;

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    final JmxReporter jmx = JmxReporter.forRegistry(metricRegistry).inDomain("metrics").build();
    final EnumSet<DispatcherType> disps
        = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

    initMetrics(servletContext, disps);

    registerReporter(jmx).start();
  }

  private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
    servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, metricRegistry);
    servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);

    final FilterRegistration.Dynamic meteredRequestsFilter
        = servletContext.addFilter("webappMetricsFilter", new InstrumentedFilter());

    meteredRequestsFilter.addMappingForUrlPatterns(disps, true, "/api/*");
    meteredRequestsFilter.setAsyncSupported(true);

    final ServletRegistration.Dynamic metricsAdminServlet
        = servletContext.addServlet("metricsServlet", new MetricsServlet());

    metricsAdminServlet.addMapping("/metrics");
    metricsAdminServlet.setAsyncSupported(true);
    metricsAdminServlet.setLoadOnStartup(2);
  }
}
