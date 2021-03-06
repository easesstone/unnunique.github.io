package com.sydney.dream

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Durations, StreamingContext}

//import kafka.serializer.StringDecoder
//import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.{Durations, StreamingContext}

object StreamingV1 {
    def main(args: Array[String]): Unit = {
        val ssc = StreamingContext.getOrCreate("/checkpoint", getStreamingContext _)
        ssc.start()
        ssc.awaitTermination()
    }

    def getStreamingContext() : StreamingContext = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreamingOnKafkaDirect")
        val ssc = new StreamingContext(conf, Durations.seconds(15))
        val kafkaParams = Map(
        "metadata.broker.list" -> "172.18.18.100:9092,172.18.18.101:9092,172.18.18.102:9092",
        "group.id" -> "DemoGroup"
        )
        val topicsSet = "first".split(",").toSet
//        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
//        messages.foreachRDD(rdd => {
//            rdd.foreach(row => {println("key: " + row._1 + ", "  + "value: " + row._2)})
//            val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//            offsetRanges.foreach(offsetRange => println("=================================================" + offsetRange))
//        })
        ssc.checkpoint("/checkpoint")
        ssc
    }
}
