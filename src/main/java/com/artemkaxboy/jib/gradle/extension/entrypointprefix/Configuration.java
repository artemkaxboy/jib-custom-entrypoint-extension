package com.artemkaxboy.jib.gradle.extension.entrypointprefix;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;

/**
 * Extension-specific Gradle configuration.
 *
 * <p>Example usage in {@code build.gradle.kts}:
 *
 * <pre>{@code
 * jib {
 *   pluginExtensions {
 *     pluginExtension {
 *       implementation = "com.artemkaxboy.jib.gradle.extension.entrypointprefix.JibEntrypointExtension"
 *       configuration(Action<com.artemkaxboy.jib.gradle.extension.entrypointprefix.Configuration> {
 *         entrypointPrefix = listOf("./wait-for-it.sh", "postgres:5432", "--")
 *       })
 *     }
 *   }
 * }
 *
 * jib {
 *   pluginExtensions {
 *     pluginExtension {
 *       implementation = "com.artemkaxboy.jib.gradle.extension.entrypointprefix.JibEntrypointExtension"
 *       configuration(Action<com.artemkaxboy.jib.gradle.extension.entrypointprefix.Configuration> {
 *         setEntrypointPrefix("./wait-for-it.sh postgres:5432 --")
 *       })
 *     }
 *   }
 * }
 * }</pre>
 */
@SuppressWarnings("UnstableApiUsage")
public class Configuration {

  public static class EntrypointPrefixSpec {

    private final Project project;
    private final ListProperty<String> entrypointPrefix;

    @Inject
    public EntrypointPrefixSpec(Project project) {
      this.project = project;
      entrypointPrefix = project.getObjects().listProperty(String.class).empty();
    }

    private ListProperty<String> getEntrypointPrefix() {
      return entrypointPrefix;
    }

    /**
     * Sets entrypoint prefix configuration.
     *
     * @param action closure representing a entrypoint prefix configuration
     */
    public void entrypointPrefix(Action<? super List<String>> action) {
      @SuppressWarnings("unchecked")
      List<String> prefix = project.getObjects().newInstance(List.class);
      action.execute(prefix);
      entrypointPrefix.set(prefix);
    }
  }

  private final EntrypointPrefixSpec entrypointPrefixSpec;

  /**
   * Constructor used to inject a Gradle project.
   *
   * @param project the injected Gradle project
   */
  @Inject
  public Configuration(Project project) {
    entrypointPrefixSpec = project.getObjects().newInstance(EntrypointPrefixSpec.class, project);
  }

  public List<String> getEntrypointPrefix() {
    return entrypointPrefixSpec.getEntrypointPrefix().get();
  }

  public void setEntrypointPrefix(String prefix) {
    setEntrypointPrefix(Arrays.asList(prefix.split(" ")));
  }

  public void setEntrypointPrefix(List<String> prefix) {
    entrypointPrefixSpec.getEntrypointPrefix().addAll(prefix);
  }
}
