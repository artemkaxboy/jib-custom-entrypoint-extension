# Custom Entrypoint extension for Google Jib Demo project

Project shows how to use [Custom Entrypoint extension](..) for [Google Jib maven plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) to customize target image entrypoint. It adds a prefix to the entrypoint which runs wait-for-it.sh. Script checks availability of port 80 at google.com and then runs the java application with arguments.

## How to build

Build docker image with maven command:

```shell
./mvnw compile jib:dockerBuild
```

Docker image will be locally created and saved into local storage with name ```jib-custom-entrypoint```.

## How to run

Run with docker command:

```shell
docker run -it jib-custom-entrypoint
```
