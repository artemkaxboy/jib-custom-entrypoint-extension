package com.artemkaxboy.jib.gradle.extension.entrypointprefix;

import com.google.cloud.tools.jib.api.buildplan.ContainerBuildPlan;
import com.google.cloud.tools.jib.gradle.extension.GradleData;
import com.google.cloud.tools.jib.gradle.extension.JibGradlePluginExtension;
import com.google.cloud.tools.jib.plugins.extension.ExtensionLogger;
import com.google.cloud.tools.jib.plugins.extension.JibPluginExtensionException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class JibEntrypointPrefixExtension implements JibGradlePluginExtension<Configuration> {

  @Override
  public Optional<Class<Configuration>> getExtraConfigType() {
    return Optional.of(Configuration.class);
  }

  @Override
  public ContainerBuildPlan extendContainerBuildPlan(
      ContainerBuildPlan buildPlan,
      Map<String, String> properties,
      Optional<Configuration> configuration,
      GradleData gradleData,
      ExtensionLogger logger)
      throws JibPluginExtensionException {

    logger.log(ExtensionLogger.LogLevel.LIFECYCLE, "Running Jib Entrypoint Prefix Extension");

    Configuration configurationValue = configuration.orElse(null);
    if (configurationValue == null) {
      logger.log(
          ExtensionLogger.LogLevel.WARN, "Nothing configured for Jib Entrypoint Prefix Extension");
      return buildPlan;
    }

    List<String> entrypoint = getNeededEntrypoint(buildPlan, configurationValue);
    if (entrypoint == null) {
      throw new JibPluginExtensionException(getClass(), "cannot get entrypoint");
    }

    List<String> resultEntrypoint =
        Stream.of(
                configuration.get().getEntrypointPrefix().stream(),
                entrypoint.stream(),
                configuration.get().getEntrypointSuffix().stream())
            .flatMap(it -> it)
            .collect(Collectors.toList());

    logger.log(
        ExtensionLogger.LogLevel.INFO, "New entrypoint is: " + String.join(" ", resultEntrypoint));

    return buildPlan.toBuilder().setEntrypoint(resultEntrypoint).build();
  }

  @Nullable
  private List<String> getNeededEntrypoint(
      ContainerBuildPlan buildPlan, Configuration configuration) {

    return configuration.hasEntryPoint()
        ? configuration.getEntrypoint()
        : buildPlan.getEntrypoint();
  }
}
