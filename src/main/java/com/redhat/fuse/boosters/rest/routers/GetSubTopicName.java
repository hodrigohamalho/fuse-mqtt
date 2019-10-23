package com.redhat.fuse.boosters.rest.routers;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetSubTopicName implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String der = (String) exchange.getIn().getHeader("CamelMqttTopic");
        der = der.substring(der.lastIndexOf("/")+1);
        exchange.getIn().setHeader("der", der);
    }

}