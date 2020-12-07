# Docker entrypoint Prefix customization extension for Google jib

## How to use

### Add buildscript.dependencies

First you need to add dependency to build.gradle.kts:

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

### Add Jib plugin

Add Jib plugin to build.gradle.kts:

```kotlin
plugins {
    ...
    // Google JIB dependency
    id("com.google.cloud.tools.jib") version "2.7.0"
}
```

### Setup entrypoint prefix

Setup entrypoint prefix in ```jib``` plugin section of build.gradle.kts:

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
