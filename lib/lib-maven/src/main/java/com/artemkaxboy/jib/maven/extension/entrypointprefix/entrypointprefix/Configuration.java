package com.artemkaxboy.jib.maven.extension.entrypointprefix.entrypointprefix;

import java.util.ArrayList;
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

  private final List<String> entrypointPrefix = new ArrayList<>();

  public List<String> getEntrypointPrefix() {
    return entrypointPrefix;
  }
}
