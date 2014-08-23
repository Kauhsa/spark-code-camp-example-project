# Example Spark project

This is an example project that you can use as starting point to your project. The only dependency you'll need is Maven (probably version 3), but if you're using machines in Department of Computer Science, it should be already installed.

## What's inside

- `pom.xml` configured to build a JAR file containing the application and all its dependencies
- Small Spark example in `src/main/scala/my/java/or/scala/Program.scala`

## How to run locally

At first, you'll need to build your project. Run the following command in shell while in the root directory of the project:

```
mvn clean compile assembly:single
```

If all went well, you should now have a `target/your-artifact-id-1.0-jar-with-dependencies.jar`.

As of Spark 1.0.2, the recommended way to run Spark applications is to use `spark-submit` script that is included in Spark distribution. This means that you'll need to download Spark distribution to somewhere in your computer. Download a pre-built package from here <https://spark.apache.org/downloads.html>. Usage details in <https://spark.apache.org/docs/latest/submitting-applications.html>.

So to run the project locally, execute the following in shell:

```
path/to/spark-submit \
    --class "my.java.or.scala.Program" \
    --master "local" \
    --jar "target/your-artifact-id-1.0-jar-with-dependencies.jar"
```

Spark likes to print a lot of... details about the execution to stderr, so if it bothers you, redirect stderr to a file (append the command with `2> stderr.log`).
