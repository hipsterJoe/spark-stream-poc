package org.example

import _root_.kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils

object Main extends App {

	val sparkConf = new SparkConf().setAppName("streamingPoc")
	val ssc = new StreamingContext(sparkConf, Seconds(1))
	ssc.checkpoint("checkpoint")

	val kafkaParams = Map(
		"zookeeper.connect" -> "localhost:2181",
		"group.id" -> "spark-streaming-poc",
		"zookeeper.connection.timeout.ms" -> "1000")

	val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set("test"))
	stream.persist()

}