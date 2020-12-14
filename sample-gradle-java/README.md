# Custom Entrypoint extension for Google Jib Demo project

Project shows how to use [Custom Entrypoint extension](https://github.com/artemkaxboy/jib-custom-entrypoint-extension-gradle) for [Google Jib gradle plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) to customize target image entrypoint.

## How to build

Build docker image with gradle command:

```shell
./gradlew jibDockerBuild
```

Docker image will be locally created and saved into local storage with name ```jib-custom-entrypoint```.

## How to run

Run with docker command:

```shell
docker run -it jib-custom-entrypoint
```
