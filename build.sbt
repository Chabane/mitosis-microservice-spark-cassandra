
resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven"

exportJars := true

name := "mitosis-microservice-spark-cassandra"
organization := "com.mitosis"
version := "1.0.0-alpha.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"
val jacksonVersion = "2.6.5"
val cassandraVersion = "2.0.5"
val log4jVersion = "1.2.14"
val typesafeVersion = "1.3.0"

libraryDependencies ++= Seq(
    "log4j" % "log4j" % log4jVersion,

    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" % "spark-streaming_2.11" % sparkVersion,
    "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % sparkVersion,

    "com.datastax.spark" %% "spark-cassandra-connector" % cassandraVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,

    "com.typesafe" % "config" % typesafeVersion,
)

