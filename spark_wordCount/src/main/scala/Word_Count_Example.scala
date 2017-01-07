import java.io.StringWriter

import org.apache.spark.sql.{Dataset, SparkSession}


object Word_Count_Example extends App {

  val sparkSession = SparkSession.builder.
    master("local")
    .appName("Word_Count_Example")
    .getOrCreate()

  val stringWriter = new StringWriter()

  def getCurrentDirectory = new java.io.File(".").getCanonicalPath


  import sparkSession.implicits._

  try {
    val data: Dataset[String] = sparkSession.read.text(getCurrentDirectory + "/src/main/resources/wordCount.txt").as[String]
    val wordsMap = data.flatMap(value => value.split("\\s+")).collect().toList.groupBy(identity).mapValues(_.size)
    println(wordsMap.prettyPrint)
  }

  catch {
    case exception: Exception => println(exception.printStackTrace())
  }

  implicit class PrettyPrintMap[K, V](val map: Map[K, V]) {
    def prettyPrint: PrettyPrintMap[K, V] = this

    override def toString: String = {
      val valuesString = toStringLines.mkString("\n")

      "Map (\n" + valuesString + "\n)"
    }
    def toStringLines = {
      map
        .flatMap{ case (k, v) => keyValueToString(k, v)}
        .map(indentLine)
    }

    def keyValueToString(key: K, value: V): Iterable[String] = {
      value match {
        case v: Map[_, _] => Iterable(key + " -> Map (") ++ v.prettyPrint.toStringLines ++ Iterable(")")
        case x => Iterable(key + " -> " + x.toString)
      }
    }

    def indentLine(line: String): String = {
      "\t" + line
    }

  }

}
