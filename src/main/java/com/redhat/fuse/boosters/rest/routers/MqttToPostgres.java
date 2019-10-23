package com.redhat.fuse.boosters.rest.routers;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class MqttToPostgres extends RouteBuilder {

	private String ds = "?dataSource=dataSource";
    public String insertSQL = "sql:insert into der_dermeasurements (der, arrival_timestamp, timestamp, samples) values (:#${header.der}, :#${header.timeNow}, to_timestamp(:#${body[timestamp]}), :#${header.bodyJson}::JSON)" + ds;

    @Override
    public void configure() throws Exception {

    	from("paho:ders/+")
            .log("Received: ${body}")
            .setHeader("timeNow", simple("${date:now}"))
            .process(new GetSubTopicName())
            .setHeader("bodyJson", body().convertTo(String.class))
            .unmarshal().json(JsonLibrary.Jackson)
            .log("Inserting into db.")
        	.to(insertSQL); 
    	
    }
        
}

