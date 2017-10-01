resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven"

name := "mitosis-microservice-spark-cassandra"
organization := "com.mitosis"
version := "1.0.0-alpha.0"
mainClass in Compile := Some("com.mitosis.App")

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"
val jacksonVersion = "2.6.5"
val cassandraVersion = "2.0.5"
val log4jVersion = "1.2.14"
val typesafeVersion = "1.3.0"
val guavaVersion = "19.0"

libraryDependencies += "log4j" % "log4j" % log4jVersion

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % sparkVersion
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % sparkVersion

libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % cassandraVersion
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion

libraryDependencies += "com.typesafe" % "config" % typesafeVersion
libraryDependencies += "com.google.guava" % "guava" % guavaVersion

