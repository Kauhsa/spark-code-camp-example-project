package my.java.or.scala

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object Program {
  def main(args: Array[String]) {
    val appName = "Cool Project"
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)
    
    
    /* In Scala, a function defined
     def function(args) = { stuff }
     returns the last value in the function, if not placed in a val or var:
    */
    val rand2 = randomNumbers(sc)
    
    println("Random2: "+rand2.mkString("[", ", ", "]"))
    // When done, stop the SparkContext:
    sc.stop
  }
  
  def randomNumbers(sc: SparkContext) = {
    /* Create a dataset of numbers 1 to 100, split to 8 partitions. */
    val r = 1 to 100
    val rdd = sc.parallelize(r, 8)
    val seed = 128 // Random seed
    /*Use a fixed seed (seed+i) for data partition a with index i */
    def rand = (i:Int, a:Iterator[Int]) => {
        val rdm = new java.util.Random(seed + i)
        a.map(x => x * rdm.nextInt)
    }
    
    /* transform the 8 partitions into random numbers using 8 different seeds,
     * 128, 129, ... , 135
     */
    val randomized = rdd.mapPartitionsWithSplit(rand)
    val rand1 = randomized.take(5)
    println("Random1: "+rand1.mkString("[", ", ", "]"))
    /* This will have the exact same result: */
    val randomized2 = rdd.mapPartitionsWithSplit(rand)
    randomized2.take(5)
  }
}
