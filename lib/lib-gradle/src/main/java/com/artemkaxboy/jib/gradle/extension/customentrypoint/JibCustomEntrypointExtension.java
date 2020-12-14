package com.artemkaxboy.jib.gradle.extension.customentrypoint;

import com.google.cloud.tools.jib.api.buildplan.ContainerBuildPlan;
import com.google.cloud.tools.jib.gradle.extension.GradleData;
import com.google.cloud.tools.jib.gradle.extension.JibGradlePluginExtension;
import com.google.cloud.tools.jib.plugins.extension.ExtensionLogger;
import com.google.cloud.tools.jib.plugins.extension.JibPluginExtensionException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class JibCustomEntrypointExtension implements JibGradlePluginExtension<Configuration> {

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

    logger.log(ExtensionLogger.LogLevel.LIFECYCLE, "Running Jib Custom Entrypoint Extension");

    /* Get valid configuration or log and exit. */
    Configuration configurationValue = configuration.orElse(null);
    if (configurationValue == null) {
      logger.log(
          ExtensionLogger.LogLevel.WARN, "Nothing configured for Jib Custom Entrypoint Extension");
      return buildPlan;
    }

    List<String> entrypoint = getNeededEntrypoint(buildPlan, configurationValue);
    if (entrypoint == null) {
      throw new JibPluginExtensionException(getClass(), "cannot get entrypoint");
    }

    List<String> resultEntrypoint =
        Stream.of(
                configurationValue.getEntrypointPrefix().stream(),
                entrypoint.stream(),
                configurationValue.getEntrypointSuffix().stream())
            .flatMap(JibCustomEntrypointExtension::splitEach)
            .collect(Collectors.toList());

    logger.log(
        ExtensionLogger.LogLevel.INFO, "New entrypoint is: " + String.join(" ", resultEntrypoint));

    return buildPlan.toBuilder().setEntrypoint(resultEntrypoint).build();
  }

  @Nullable
  private static List<String> getNeededEntrypoint(
      ContainerBuildPlan buildPlan, Configuration configuration) {

    return configuration.hasEntryPoint()
        ? configuration.getEntrypoint()
        : buildPlan.getEntrypoint();
  }

  /* Cannot split in Configuration because maven set even private variables directly
  without setters. */
  private static Stream<String> splitEach(Stream<String> stream) {
    return stream.flatMap(it -> Arrays.stream(it.split(" ")));
  }
}
