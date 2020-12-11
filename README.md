# Docker entrypoint Prefix customization extension for Google jib

## How to use

### Gradle

#### Add buildscript.dependencies

```build.gradle```:

```groovy
// should be at the top of build.gradle
buildscript {

    dependencies {
        classpath "com.artemkaxboy:jib-custom-entrypoint-extension-gradle:0.0.1"
    }
}
```

or ```build.gradle.kts```:

```kotlin
// should be at the top of build.gradle.kts
buildscript {

    dependencies {
        classpath("com.artemkaxboy:jib-custom-entrypoint-extension-gradle:0.0.1")
    }
}
```

#### Add Jib plugin

```build.gradle```/```build.gradle.kts```:

```groovy
plugins {
    ...
    // Google JIB dependency
    id("com.google.cloud.tools.jib") version "2.7.0"
}
```

#### Setup entrypoint prefix

```build.gradle.kts```:

```kotlin
jib {
    ...
    pluginExtensions {
        pluginExtension {
            implementation = "JibEntrypointPrefixExtension"
            configuration(Action<Configuration> {
                setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
            })
        }
    }
}
```

or ```build.gradle```:

```groovy
jib {
    ...
    pluginExtensions {
        pluginExtension {
            implementation = "JibEntrypointPrefixExtension"
            configuration {
                setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
            }
        }
    }
}
```
