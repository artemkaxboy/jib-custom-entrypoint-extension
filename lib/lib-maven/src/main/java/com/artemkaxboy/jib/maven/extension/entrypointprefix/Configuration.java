package com.artemkaxboy.jib.maven.extension.entrypointprefix;

import java.util.LinkedList;
import java.util.List;

/**
 * Extension-specific Maven configuration.
 *
 * <p>Example usage in {@code pom.xml}:
 *
 * <pre>{@code
 * <configuration implementation="com.google.cloud.tools.jib.maven.extension.ownership.Configuration">
 *   <entrypointPrefix>./wait-for-it.sh</entrypointPrefix>
 *   <entrypointPrefix>postgres:5432</entrypointPrefix>
 *   <entrypointPrefix>--</entrypointPrefix>
 * </configuration>
 * }</pre>
 */
public class Configuration {

  private final List<String> entrypointPrefix = new LinkedList<>();
  private final List<String> entrypoint = new LinkedList<>();
  private final List<String> entrypointSuffix = new LinkedList<>();

  public List<String> getEntrypointPrefix() {
    return entrypointPrefix;
  }

  boolean hasEntryPoint() {
    return getEntrypoint().size() > 0;
  }

  public List<String> getEntrypoint() {
    return entrypoint;
  }

  public List<String> getEntrypointSuffix() {
    return entrypointSuffix;
  }
}
