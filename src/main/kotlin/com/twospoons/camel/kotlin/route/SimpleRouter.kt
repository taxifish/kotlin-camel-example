package com.twospoons.camel.kotlin.route

import com.twospoons.camel.kotlin.service.SimpleService
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.camel.LoggingLevel.*
import org.apache.camel.model.dataformat.JsonLibrary

@Component
class SimpleRouter : RouteBuilder() {

    @Autowired
    lateinit var simpleService: SimpleService

    override fun configure() {
        from("direct:test-input")
                .log(DEBUG, log,"Received message on test-input")
                .bean(simpleService, "simpleInputFunction")
                .choice()
                .`when`(header("SEND_OUT").isNotNull)
                    .log(DEBUG, log,"Message is valid and will be sent to direct:test-output")
                    .to("direct:test-output")
                .endChoice()


        from("direct:test-output")
                .log(DEBUG, log, "Received message on test-output")
                .bean(simpleService, "simpleOutputFunction")
                .to("log:out")

        from("web3j://http://104.40.193.0:8545?operation=BLOCK_OBSERVABLE&fullTransactionObjects=true")
                .marshal().json(JsonLibrary.Gson)
                .convertBodyTo(String::class.java)
                .to("mongodb:mongoLocal?database=rinkeby&collection=blocks&operation=insert")

    }


}