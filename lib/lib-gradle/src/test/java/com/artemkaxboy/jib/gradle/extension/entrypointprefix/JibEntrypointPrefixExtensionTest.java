package com.artemkaxboy.jib.gradle.extension.entrypointprefix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.cloud.tools.jib.api.buildplan.ContainerBuildPlan;
import com.google.cloud.tools.jib.plugins.extension.ExtensionLogger;
import com.google.cloud.tools.jib.plugins.extension.JibPluginExtensionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JibEntrypointPrefixExtensionTest {

  private static final List<String> defaultEntrypoint =
      Arrays.asList("java", "-cp", "/app/resources:/app/classes:/app/libs/*", "main.class");

  @Mock private Configuration configuration;

  @Mock private ExtensionLogger logger;

  private final ContainerBuildPlan buildPlan =
      ContainerBuildPlan.builder().setEntrypoint(defaultEntrypoint).build();

  @Test
  public void testExtendContainerBuildPlan_noEntrypoint() {
    ContainerBuildPlan buildPlan = ContainerBuildPlan.builder().build();

    assertThrows(
        JibPluginExtensionException.class,
        () ->
            new JibEntrypointPrefixExtension()
                .extendContainerBuildPlan(
                    buildPlan, null, Optional.of(configuration), null, logger));
  }

  @Test
  public void testExtendContainerBuildPlan_noConfiguration() throws JibPluginExtensionException {
    ContainerBuildPlan newPlan =
        new JibEntrypointPrefixExtension()
            .extendContainerBuildPlan(buildPlan, null, Optional.empty(), null, logger);

    assertSame(buildPlan, newPlan);

    verify(logger)
        .log(
            ExtensionLogger.LogLevel.WARN,
            "Nothing configured for Jib Entrypoint Prefix Extension");
  }

  @Test
  public void testExtendContainerBuildPlan() throws JibPluginExtensionException {
    List<String> prefix = Arrays.asList("echo", "old", "entrypoint", "was", ":");
    when(configuration.getEntrypointPrefix()).thenReturn(prefix);

    List<String> expected = new ArrayList<>(prefix);
    expected.addAll(defaultEntrypoint);

    ContainerBuildPlan newPlan =
        new JibEntrypointPrefixExtension()
            .extendContainerBuildPlan(buildPlan, null, Optional.of(configuration), null, logger);

    assertEquals(expected, newPlan.getEntrypoint());
  }
}
