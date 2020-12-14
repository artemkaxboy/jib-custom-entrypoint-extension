# Jib Custom Entrypoint Extension

A general-purpose extension that enables to customize an image's entrypoint by adding prefix/suffix to the original entrypoint or override it completely.

## Examples

Check out the [general instructions](https://github.com/GoogleContainerTools/jib-extensions/blob/master/README.md) for applying a Jib plugin extension.

```gradle
// should be at the top of build.gradle
buildscript {
  dependencies {
    classpath('com.artemkaxboy:jib-custom-entrypoint-extension-gradle:0.1.0')
  }
}

...

jib {
  ...
  pluginExtensions {
    pluginExtension {
      implementation = 'com.artemkaxboy.jib.gradle.extension.customentrypoint.JibCustomEntrypointExtension'
      configuration {
        setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
        setEntrypointSuffix("argument1 argument2")
      }
    }
  }
}
```

Kotlin requires specifying the type for `pluginExtension.configuration`.

```kotlin
  pluginExtension {
    implementation = "com.artemkaxboy.jib.gradle.extension.customentrypoint.JibCustomEntrypointExtension"
    configuration(Action<com.artemkaxboy.jib.gradle.extension.customentrypoint.Configuration> {
        setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
        setEntrypointSuffix("argument1 argument2")
    })
  }
```

## Conventions

-   Omitting `setEntrypointPrefix`/`setEntrypointSuffix` leaves suffix/prefix empty.
-   Omitting `setEntrypoint` leaves original one untouched.
