# Scale Java Blog Project

The idea is to build a toy blog site running on Java using native images. This started as an experiment
but seemed good enough to be shared with other good folks of open source world.

# Running it on a server
Steps
### Get postgres 
Download and install postgres, else use docker-compose file from within the repository to set up.

### Run
You can grab the latest version from [Github Packages](https://github.com/supaldubey/blog-server/packages/373751). 

Download the [Runner File](https://github-production-registry-package-file-4f11e5.s3.amazonaws.com/283811232/0d421280-e7cc-11ea-995c-940b019f4764?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20200827%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20200827T033615Z&X-Amz-Expires=300&X-Amz-Signature=93d9ccb762de376cff7f1cc43e7b9fe898b91fa8df021dbe47661c307b218851&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=0&response-content-disposition=filename%3Dcube-blog-1.0.1-runner.jar&response-content-type=application%2Foctet-stream) from the latest release version. 

Use below command to override the local defaults 

```
java -Dquarkus.datasource.password=no-one-knows \
    -Dquarkus.datasource.username=probably-few-know \
    -Dquarkus.datasource.jdbc.url=jdbc:postgresql://my-blog-host:5432/my-awesome-db \
    -Djwt.secret=My-Super-Secret-String \
    -jar cube-blog-1.0.1-runner.jar \
```

This project uses Quarkus.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `cube-blog-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/cube-blog-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/cube-blog-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

