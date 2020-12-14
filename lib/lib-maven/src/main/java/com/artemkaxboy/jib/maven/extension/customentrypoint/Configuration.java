package com.artemkaxboy.jib.maven.extension.customentrypoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Extension-specific Maven configuration.
 *
 * <p>Example usage in {@code pom.xml}:
 *
 * <pre>{@code
 * <configuration implementation="com.artemkaxboy.jib.maven.extension.customentrypoint.Configuration">
 *   <entrypointPrefix>/files/wait-for-it.sh google.com:80 --</entrypointPrefix>
 *   <entrypointSuffix>argument1 argument2</entrypointSuffix>
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
