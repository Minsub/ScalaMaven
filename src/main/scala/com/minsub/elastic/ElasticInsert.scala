package com.minsub.elastic

import java.net.InetAddress

import org.elasticsearch.action.bulk.{BulkRequestBuilder, BulkResponse}
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentFactory._
import org.elasticsearch.transport.client.PreBuiltTransportClient

object ElasticInsert {


  def main(args: Array[String]): Unit = {

    val settings: Settings = Settings.builder().put("cluster.name","elasticsearch").build()

    val client: TransportClient = new PreBuiltTransportClient(settings)
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))

    //val response: GetResponse = client.prepareGet("classes","class","14").get()
    //println(response.getSource)

    val request: BulkRequestBuilder = client.prepareBulk()

    request.add(client.prepareIndex("test-java","java")
                .setSource(jsonBuilder().startObject().field("name", "minsub").field("age", 5).endObject()))

    request.add(client.prepareIndex("test-java","java")
      .setSource(jsonBuilder().startObject().field("name", "brown").field("age", 28).endObject()))


    val response: BulkResponse =  request.get()

    println("finish. is failure: " + response.hasFailures)

    client.close();
  }
}
