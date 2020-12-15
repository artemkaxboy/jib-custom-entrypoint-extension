# Jib Custom Entrypoint Extension

A general-purpose extension that enables to customize an image's entrypoint by adding prefix/suffix to the original entrypoint or override it completely.

## How it works

Extensions allow one to customize Google Jib Docker image entrypoint by adding specific prefixes/suffixes to the original one and therefore to avoid overriding classpath and main-class.

Example:

Default entrypoint: ```java -cp /app/resources:/app/classes:/app/libs/* Application``` after adding prefix ```./wait-for-it.sh google.com:80 --``` becomes ```./wait-for-it.sh google.com:80 -- java -cp /app/resources:/app/classes:/app/libs/* Application``` which makes health-check requests before running the application.

## Extensions and Examples

### Gradle

[Extension Documentation/Source code](lib/lib-gradle)

Sample projects: [Kotlin](sample-gradle-kotlin)/[Java](sample-gradle-java)

### Maven

[Extension Documentation/Source code](lib/lib-maven)

Sample project: [Java](sample-maven-java)
