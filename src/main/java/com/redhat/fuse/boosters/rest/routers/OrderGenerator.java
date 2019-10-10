package com.redhat.fuse.boosters.rest.routers;

import com.redhat.fuse.boosters.rest.service.OrderService;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderGenerator extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:generate")
            .bean(OrderService.class, "generateOrder")
            .log("Order ${body.item} generated")
            .convertBodyTo(String.class)
        .to("direct:send-to-mqtt");

        from("direct:send-to-mqtt")
            .log("sending message ${body} to broker via MQTT")
        .to("mqtt:magda?publishTopicName=magdaTopic");
        
        from("mqtt:magda?subscribeTopicName=magdaTopic")
            .log("Consuming message ${body}");
        }
        
}

