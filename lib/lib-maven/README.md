# Jib Custom Entrypoint Extension

A general-purpose extension that enables to customize an image's entrypoint by adding prefix/suffix to the original entrypoint or override it completely.

## Examples

Check out the [general instructions](https://github.com/GoogleContainerTools/jib-extensions/blob/master/README.md) for applying a Jib plugin extension.

```xml
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>2.7.0</version>

    <dependencies>
        <dependency>
            <groupId>com.artemkaxboy</groupId>
            <artifactId>jib-custom-entrypoint-extension-maven</artifactId>
            <version>0.1.0</version>
        </dependency>
    </dependencies>

    <configuration>
        ...
        <pluginExtensions>
            <pluginExtension>
                <implementation>com.artemkaxboy.jib.maven.extension.customentrypoint.JibCustomEntrypointExtension</implementation>
                <configuration implementation="com.artemkaxboy.jib.maven.extension.customentrypoint.Configuration">
                    <entrypointPrefix>/files/wait-for-it.sh google.com:80 --</entrypointPrefix>
                    <entrypointSuffix>maven-java suffix</entrypointSuffix>
                </configuration>
            </pluginExtension>
        </pluginExtensions>
    </configuration>
</plugin>
```

## Conventions

-   Omitting `setEntrypointPrefix`/`setEntrypointSuffix` leaves suffix/prefix empty.
-   Omitting `setEntrypoint` leaves original one untouched.
