package com.artemkaxboy.jib.gradle.extension.customentrypoint;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
 *       implementation = "com.artemkaxboy.jib.gradle.extension.customentrypoint.JibCustomEntrypointExtension"
 *       configuration(Action<com.artemkaxboy.jib.gradle.extension.customentrypoint.Configuration> {
 *         entrypointPrefix = listOf("./wait-for-it.sh", "postgres:5432", "--")
 *       })
 *     }
 *   }
 * }
 *
 * jib {
 *   pluginExtensions {
 *     pluginExtension {
 *       implementation = "com.artemkaxboy.jib.gradle.extension.customentrypoint.JibCustomEntrypointExtension"
 *       configuration(Action<com.artemkaxboy.jib.gradle.extension.customentrypoint.Configuration> {
 *         setEntrypointPrefix("./wait-for-it.sh postgres:5432 --")
 *       })
 *     }
 *   }
 * }
 * }</pre>
 */
@SuppressWarnings("UnstableApiUsage")
public class Configuration {

  public static class EntrypointSpec {

    private final Project project;
    private final ListProperty<String> entrypointPrefix;
    private final ListProperty<String> entrypoint;
    private final ListProperty<String> entrypointSuffix;

    @Inject
    public EntrypointSpec(Project project) {
      this.project = project;
      entrypointPrefix = project.getObjects().listProperty(String.class).empty();
      entrypoint = project.getObjects().listProperty(String.class).empty();
      entrypointSuffix = project.getObjects().listProperty(String.class).empty();
    }

    private ListProperty<String> getEntrypointPrefix() {
      return entrypointPrefix;
    }

    private ListProperty<String> getEntrypoint() {
      return entrypoint;
    }

    private ListProperty<String> getEntrypointSuffix() {
      return entrypointSuffix;
    }

    /**
     * Sets custom entrypoint configuration.
     *
     * @param action closure representing a custom entrypoint configuration
     */
    public void entrypointPrefix(Action<? super List<String>> action) {
      @SuppressWarnings("unchecked")
      List<String> prefix = project.getObjects().newInstance(List.class);
      action.execute(prefix);
      entrypointPrefix.set(prefix);
    }

    /**
     * Sets entrypoint configuration.
     *
     * @param action closure representing an entrypoint configuration
     */
    public void entrypoint(Action<? super List<String>> action) {
      @SuppressWarnings("unchecked")
      List<String> prefix = project.getObjects().newInstance(List.class);
      action.execute(prefix);
      entrypoint.set(prefix);
    }

    /**
     * Sets entrypoint suffix configuration.
     *
     * @param action closure representing an entrypoint suffix configuration
     */
    public void entrypointSuffix(Action<? super List<String>> action) {
      @SuppressWarnings("unchecked")
      List<String> prefix = project.getObjects().newInstance(List.class);
      action.execute(prefix);
      entrypointSuffix.set(prefix);
    }
  }

  private final EntrypointSpec entrypointSpec;

  /**
   * Constructor used to inject a Gradle project.
   *
   * @param project the injected Gradle project
   */
  @Inject
  public Configuration(Project project) {
    entrypointSpec = project.getObjects().newInstance(EntrypointSpec.class, project);
  }

  public List<String> getEntrypointPrefix() {
    return entrypointSpec.getEntrypointPrefix().get();
  }

  public void setEntrypointPrefix(String prefix) {
    entrypointSpec.getEntrypointPrefix().set(toList(prefix));
  }

  public void setEntrypointPrefix(List<String> prefix) {
    entrypointSpec.getEntrypointPrefix().addAll(prefix);
  }

  boolean hasEntryPoint() {
    return entrypointSpec.getEntrypoint().get().size() > 0;
  }

  public List<String> getEntrypoint() {
    return entrypointSpec.getEntrypoint().get();
  }

  public void setEntrypoint(String entrypoint) {
    entrypointSpec.getEntrypoint().set(toList(entrypoint));
  }

  public void setEntrypoint(List<String> entrypoint) {
    entrypointSpec.getEntrypoint().addAll(entrypoint);
  }

  public List<String> getEntrypointSuffix() {
    return entrypointSpec.getEntrypointSuffix().get();
  }

  public void setEntrypointSuffix(String suffix) {
    entrypointSpec.getEntrypointSuffix().set(toList(suffix));
  }

  public void setEntrypointSuffix(List<String> suffix) {
    entrypointSpec.getEntrypointSuffix().addAll(suffix);
  }

  public List<String> toList(String command) {
    return Arrays.stream(command.split(" "))
        .filter((it) -> !it.isEmpty())
        .collect(Collectors.toList());
  }
}
