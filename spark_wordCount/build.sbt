name := "spark_wordCount"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver sonatypeRepo "public",
  Resolver typesafeRepo "releases"
)

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.1"




    