/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mitosis

import java.io.IOException
import java.sql.Timestamp
import java.util.Properties

import com.mitosis.beans.CellBean
import com.mitosis.beans.CellType
import com.mitosis.config.ConfigurationFactory
import com.mitosis.utils.JsonUtils
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SQLContext, SaveMode, SparkSession}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.sql.{SaveMode, SparkSession}

object App {
  
  private[this] lazy val logger = Logger.getLogger(getClass)

  private[this] val config = ConfigurationFactory.load()

  /**
    * Json decode UDF function
    *
    * @param text the encoded JSON string
    * @return Returns record bean
    */
  def jsonDecode(text: String): CellBean = {
    try {
      print(text)
      JsonUtils.deserialize(text, classOf[CellBean])
    } catch {
      case e: IOException =>
        logger.error(e.getMessage, e)
        null
    }
  }

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder
        .appName("mitosis-microservice-spark-cassandra")
        .master("local[2]")
        .getOrCreate()
        
    sparkSession.conf.set("spark.cassandra.connection.host", config.streaming.db.host)
    sparkSession.conf.set("spark.cassandra.connection.native.port", config.streaming.db.port);

    println("sparkSession.sparkContext>"+sparkSession.sparkContext)
    val streamingContext = new StreamingContext(sparkSession.sparkContext, Seconds(config.streaming.window))

    val servers = config.producer.hosts.toArray.mkString(",")

    val sqlContext = new SQLContext(sparkSession.sparkContext)

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> servers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "latest",
      "group.id" -> "mitosis",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // topic names which will be read
    val topics = Array(config.producer.topic)
    
    val stream = KafkaUtils.createDirectStream(
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    
    import com.datastax.spark.connector.streaming._
		import com.datastax.spark.connector._

		stream.foreachRDD(rdd => { 
		  if (!rdd.isEmpty()) { 
        rdd.map(record => {
           val result = jsonDecode(record.value)
           (result.name, result.getType) 
        }).saveToCassandra("mitosis", config.streaming.db.table, SomeColumns("name", "celltype")) 
      } else {
       println("No records to save")
      }
		})

    // create streaming context and submit streaming jobs
    streamingContext.start()

    // wait to killing signals etc.
    streamingContext.awaitTermination()
  }
}