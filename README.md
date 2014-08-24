# Example Spark project

*This guide contains a lot of shell commands with example paths – when running these commands, lease double-check you're using correct paths that apply in your case!*

This is an example project that you can use as starting point to your project. The only extra dependency you'll need is Maven (probably version 3), but if you're using machines in Department of Computer Science, it should be already installed.

## What's inside

- `pom.xml` configured to build a JAR file containing the application and all its dependencies
- Small Spark example in `src/main/scala/my/java/or/scala/Program.scala`

## How to run locally

At first, you'll need to build your project. Run the following command in shell while in the root directory of the project:

```
mvn clean compile assembly:single
```

If all went well, you should now have a `target/your-artifact-id-1.0-jar-with-dependencies.jar`.

As of Spark 1.0.2, the recommended way to run Spark applications is to use `spark-submit` script that is included in Spark distribution. This means that you'll need to download Spark distribution to somewhere in your computer. Download a pre-built package (Hadoop v1) from here <https://spark.apache.org/downloads.html>. Usage details in <https://spark.apache.org/docs/latest/submitting-applications.html>.

So to run the project locally, execute the following in shell:

```
path/to/spark-distribution/bin/spark-submit \
    --class "my.java.or.scala.Program" \
    --master "local" \
    "target/your-artifact-id-1.0-jar-with-dependencies.jar"
```

Spark likes to print a lot of... details about the execution to stderr, so if it bothers you, redirect stderr to a file (append the command with `2> stderr.log`).

Since the commands can get quite long, creating some helper scripts for yourself is helpful.

## How to run in the Ukko cluster

First, you'll need to setup Spark in the Ukko cluster. See the [guide to setup Spark in Ukko](SPARK_IN_UKKO.md) for instructions.

Next step is to copy your JAR to `/cs/taatto/scratch`, this can be done using SCP:

```
scp target/your-artifact-id-1.0-jar-with-dependencies.jar \
    melkinkari.cs.helsinki.fi:/cs/taatto/scratch/your_username/your-artifact-id-1.0-jar-with-dependencies.jar
```

After this is done, run the following **in your master Ukko node** (correct the paths):

```
/cs/taatto/scratch/your_username/path_to_spark/bin/spark-submit \
    --class "my.java.or.scala.Program" \
    --master "spark://ukkoXXX:7077" \
    "/cs/taatto/scratch/your_username/your-artifact-id-1.0-jar-with-dependencies.jar"
```

Again, it is a good idea to redirect stderr (and maybe even stdout) to a file as we did when running the code locally. There is an example shell script called `run-spark102.sh` to launch your project in cluster, but you need to correct the paths inside to use it. Also, if your process takes a long time to run, it's a good idea to run it inside a `screen` session (or equivalent) so it doesn't get killed if your SSH connection disconnects or something else happens.

Also, at some point, you might want to tune the memory allocation of slaves and other Spark options – see <http://spark.apache.org/docs/latest/submitting-applications.html> and <http://spark.apache.org/docs/latest/configuration.html#spark-properties> for details.
