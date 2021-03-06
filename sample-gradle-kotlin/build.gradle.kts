// https://github.com/GoogleContainerTools/jib-extensions#gradle
// According to the official user guide, buildscript dependencies must be at the very beginning
// of the build.gradle/build.gradle.kts

buildscript {

    dependencies {
        classpath("com.artemkaxboy:jib-custom-entrypoint-extension-gradle:0.1.0")
    }
}

repositories {
    mavenCentral()
}

plugins {

    // We love kotlin
    kotlin("jvm") version "1.4.10"

    // Google JIB dependency
    id("com.google.cloud.tools.jib") version "2.7.0"
}

group = "com.artemkaxboy"
// version will be used as a docker image tag
version = "1.0-SNAPSHOT"

jib {

    // default image does not contain anything except java, even shell, but adoptopenjdk does
    // we need it to run our wait-for-it.sh
    from {
        image = "adoptopenjdk:11-jdk"
    }

    // override default image name
    to {
        image = "jib-custom-entrypoint"
    }

    // copy custom script from current directory into image and make it executable
    extraDirectories {

        paths {
            path {
                setFrom("files")
                into = "/files"

            }
        }

        permissions = mapOf("/files/wait-for-it.sh" to "755")
    }

    // https://docs.docker.com/compose/startup-order/
    // According the official docker guide, https://github.com/vishnubob/wait-for-it is one of
    // recommended ways to control containers startup order.

    // Add prefix to an image entrypoint which runs wait-for-it.sh. Script checks availability of
    // port 80 at google.com and then runs the kotlin application with arguments.
    pluginExtensions {
        pluginExtension {
            implementation = "com.artemkaxboy.jib.gradle.extension.customentrypoint.JibCustomEntrypointExtension"
            @Suppress("RedundantSamConstructor")
            configuration(Action<com.artemkaxboy.jib.gradle.extension.customentrypoint.Configuration> {
                setEntrypointPrefix("/files/wait-for-it.sh google.com:80 --")
                setEntrypointSuffix("gradle-kotlin suffix")
            })
        }
    }
}
