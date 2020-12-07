# Docker entrypoint Prefix customization extension for Google jib

## How to use

### Gradle

#### Add buildscript.dependencies

```build.gradle```:

```groovy
// should be at the top of build.gradle
buildscript {

    repositories {
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        classpath "com.artemkaxboy:jib-entrypoint-prefix-extension-gradle:0.1.1"
    }
}
```

or ```build.gradle.kts```:

```kotlin
// should be at the top of build.gradle.kts
buildscript {

    repositories {
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath("com.artemkaxboy:jib-entrypoint-prefix-extension-gradle:0.1.1")
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
            implementation = "com.artemkaxboy.jib.gradle.extension.entrypointprefix.JibEntrypointPrefixExtension"
            configuration(Action<com.artemkaxboy.jib.gradle.extension.entrypointprefix.Configuration> {
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
            implementation = "com.artemkaxboy.jib.gradle.extension.entrypointprefix.JibEntrypointPrefixExtension"
            configuration {
                setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
            }
        }
    }
}
```
