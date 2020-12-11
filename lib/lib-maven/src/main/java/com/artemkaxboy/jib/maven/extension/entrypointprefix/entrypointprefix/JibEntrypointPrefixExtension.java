package com.artemkaxboy.jib.maven.extension.entrypointprefix.entrypointprefix;

import com.google.cloud.tools.jib.api.buildplan.ContainerBuildPlan;
import com.google.cloud.tools.jib.maven.extension.JibMavenPluginExtension;
import com.google.cloud.tools.jib.maven.extension.MavenData;
import com.google.cloud.tools.jib.plugins.extension.ExtensionLogger;
import com.google.cloud.tools.jib.plugins.extension.JibPluginExtensionException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JibEntrypointPrefixExtension implements JibMavenPluginExtension<Configuration> {

  @Override
  public Optional<Class<Configuration>> getExtraConfigType() {
    return Optional.of(Configuration.class);
  }

  @Override
  public ContainerBuildPlan extendContainerBuildPlan(
      ContainerBuildPlan buildPlan,
      Map<String, String> properties,
      Optional<Configuration> configuration,
      MavenData mavenData,
      ExtensionLogger logger)
      throws JibPluginExtensionException {
    logger.log(ExtensionLogger.LogLevel.LIFECYCLE, "Running Jib Entrypoint Prefix Extension");

    List<String> entrypoint = buildPlan.getEntrypoint();
    if (entrypoint == null) {
      throw new JibPluginExtensionException(getClass(), "cannot get image entrypoint");
    }

    if (!configuration.isPresent()) {
      logger.log(
          ExtensionLogger.LogLevel.WARN, "Nothing configured for Jib Entrypoint Prefix Extension");
      return buildPlan;
    }

    entrypoint.addAll(0, configuration.get().getEntrypointPrefix());

    return buildPlan.toBuilder().setEntrypoint(entrypoint).build();
  }
}
