#!/bin/bash
# Runs $class on the cluster of $master with given command line arguments.
# Logs are saved to "$class".log and "$class".err in the current directory.

class="my.java.or.scala.Program"
master="ukkoxxx.hpc.cs.helsinki.fi"
jar=/cs/taatto/scratch/$USER/your-artifact-id-1.0-jar-with-dependencies.jar
/cs/taatto/scratch/$USER/spark/bin/spark-submit --class "$class" \
--master "$master" "$jar" \
$@ 1>"$class".log 2>"$class".err


